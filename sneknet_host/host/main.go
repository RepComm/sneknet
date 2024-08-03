package main

import (
	"bytes"
	"encoding/hex"
	"errors"
	"fmt"
	"log"
	"net"
	"os"
	"sneknet/common"
	"time"

	"github.com/google/gopacket"
	"github.com/google/gopacket/layers"
	"github.com/songgao/packets/ethernet"
)

func main() {
	shouldExit := make(chan bool)

	//adb forward to enable a connection between golang app and android app
	androidBridgePort := uint16(10209)
	log.Println("bridge: starting on port", androidBridgePort)
	bridge := common.NewBridge(androidBridgePort)

	bridge.OnFatal = func(err error) {
		shouldExit <- true
		panic(errors.Join(
			errors.New("bridge: fatal error"),
			err,
		))
	}
	bridge.OnData = func(data []byte, len int) {
		fmt.Println(string(data))

		//TODO - handle data from android app to send back into tuntap dev
	}

	//start the bridge
	err := bridge.Start()
	if err != nil {
		shouldExit <- true
		panic(errors.Join(errors.New("bridge: error"), err))
	}
	log.Println("bridge: started")
	defer bridge.Close()

	//declare our tap name, will show in ip/ifconfig commands
	ifname := "sneknet_tap0"

	//check if interface already exists
	log.Println("tap: check exists", ifname)
	exists, err := common.IfaceExists(ifname)
	if err != nil {
		shouldExit <- true
		panic(errors.Join(
			fmt.Errorf("cannot check if interface %s exists", ifname),
			err,
		))
	}

	//try deleting it if it does, we have to recreate to control it
	if exists {
		log.Println("tap: exists, attempting delete", ifname)
		err = common.DeleteIface(ifname)
		if err != nil {
			shouldExit <- true
			panic(errors.Join(
				fmt.Errorf("cannot delete interface %s", ifname),
				err,
			))
		}
	} else {
		log.Println("tap: doesnt exist, no need to delete")
	}

	log.Println("tap: creating", ifname)
	iface, err := common.NewIface(ifname)
	if err != nil {
		shouldExit <- true
		panic(errors.Join(
			fmt.Errorf("cannot create interface %s", ifname),
			err,
		))
	}
	log.Println("tap: successfully created", ifname)

	//defer closure of tap interface
	defer iface.Down()
	defer iface.Close()

	gatewayStaticIpv4 := "10.0.0.2"
	gatewayStaticIpv4Bin := net.ParseIP(gatewayStaticIpv4).To4()
	gatewayMac := []byte{
		0xDE, 0xAD, 0xBE, 0xEF, 0xAB, 0xCD,
	}
	broadcastMac := []byte{
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	}
	hostStaticIpv4 := "10.0.0.1"
	hostStaticIpv4Bin := net.ParseIP(hostStaticIpv4).To4()

	var pEth layers.Ethernet
	var pIPv4 layers.IPv4
	var pARP layers.ARP
	var pTCP layers.TCP
	var pUDP layers.UDP
	var pICMPv4 layers.ICMPv4

	autoPacketParser := gopacket.NewDecodingLayerParser(
		layers.LayerTypeEthernet,
		&pEth,
		&pIPv4,
		&pARP,
		&pTCP,
		&pUDP,
		&pICMPv4,
	)
	autoPacketParser.IgnorePanic = true

	decoded := []gopacket.LayerType{}

	//listen to frames
	iface.OnFrame = func(frame ethernet.Frame) {

		//try to decode into our specific layers of interest
		decodeErr := autoPacketParser.DecodeLayers(frame, &decoded)
		if decodeErr != nil {
			// log.Println("DECODE: error, skipping:", decodeErr)
			return
		}

		for _, layerType := range decoded {
			switch layerType {
			case layers.LayerTypeICMPv4:
				bridge.HandleICMPv4(&pIPv4, &pICMPv4)
			case layers.LayerTypeTCP:
				bridge.HandleTCP(&pIPv4, &pTCP)
				return
			case layers.LayerTypeARP:
				// reqArpL := packet.Layer(layers.LayerTypeARP)
				// reqArpP, _ := reqArpL.(*layers.ARP)
				reqArpP := pARP

				arpIsReq := reqArpP.Operation == layers.ARPRequest
				arpIsBroadcast := bytes.Equal(reqArpP.DstHwAddress, broadcastMac)
				arpLookingForGateway := bytes.Equal(reqArpP.DstProtAddress, gatewayStaticIpv4Bin)
				//we only care about ARP requests looking for our gateway
				if !arpIsReq || !arpIsBroadcast || !arpLookingForGateway {
					log.Printf("ARP: unhandled, is broadcast: %t, is request: %t, is looking for gateway: %t\n", arpIsBroadcast, arpIsReq, arpLookingForGateway)
					return
				}
				//response
				resArpL := &layers.ARP{
					SourceHwAddress:   gatewayMac,
					DstHwAddress:      reqArpP.SourceHwAddress,
					AddrType:          layers.LinkTypeEthernet,
					Protocol:          layers.EthernetTypeIPv4,
					HwAddressSize:     6,
					ProtAddressSize:   4,
					Operation:         layers.ARPReply,
					SourceProtAddress: gatewayStaticIpv4Bin,
					DstProtAddress:    hostStaticIpv4Bin,
				}

				resBuffer := gopacket.NewSerializeBuffer()
				err := gopacket.SerializeLayers(
					resBuffer, gopacket.SerializeOptions{
						ComputeChecksums: true,
						FixLengths:       true,
					},
					&layers.Ethernet{
						SrcMAC:       gatewayMac,
						DstMAC:       reqArpP.SourceHwAddress,
						EthernetType: layers.EthernetTypeARP,
					},
					resArpL,
				)
				if err != nil {
					log.Println("ARP: failed gateway resolution reply serialization", err)
					return
				}

				_, err = iface.Write(resBuffer.Bytes())
				if err != nil {
					log.Println("ARP: failed gateway resolution reply send", err)
					return
				}
				log.Println(
					"ARP: resolved sneknet gateway for client at",
					hex.EncodeToString(reqArpP.SourceHwAddress),
				)
				return
			}
		}

		//TODO - handle captured packet from host and try to process it and
		//forward over android bridge
	}
	iface.OnError = func(ifaceErr error) {
		shouldExit <- true
		panic(errors.Join(
			fmt.Errorf("tap: interface error on %s", ifname),
			ifaceErr,
		))
	}

	iface.Start()
	log.Println("tap: listening to ethernet frames")
	_netmask := "24"
	log.Printf("tap: setting CIDR %s/%s\n", hostStaticIpv4, _netmask)
	err = iface.SetCIDR(hostStaticIpv4, _netmask)
	if err != nil {
		shouldExit <- true
		panic(errors.Join(fmt.Errorf("tap: failed to set CIDR of iface %s", ifname),
			err,
		))
	}

	log.Printf("tap: setting %s state UP\n", ifname)
	err = iface.Up()
	if err != nil {
		shouldExit <- true
		panic(errors.Join(fmt.Errorf("tap: failed to set %s state UP", ifname),
			err,
		))
	}
	log.Printf("tap: set %s state UP\n", ifname)

	//setup default gateway so host OS thinks it can send us all traffic as it would a router
	//our default gateway isn't real, they're both this software, and we rewrite source IPs in the end anyways
	log.Println("tap: setting default gateway", ifname)
	defaultGatewayAddr := "10.0.0.2"
	err = iface.AddDefaultGateway(defaultGatewayAddr)
	if err != nil {
		shouldExit <- true
		panic(errors.Join(
			fmt.Errorf("cannot set interface %s default gateway to %s", ifname, defaultGatewayAddr),
			err,
		))
	}
	log.Println("tap: set default gateway", ifname)

	log.Println("cmd: processing stdin")

	cmdsys := common.StdLineCmds{}
	cmdsys.Start(func(cmd common.StdLineCmd) {
		log.Println(cmd.Cmd)
	})
	defer cmdsys.Stop()

	common.HandleSigTerm(func(sig os.Signal) {
		log.Println("CTRL+C detected")
		//allow some stdout flushing
		time.Sleep(100 * time.Millisecond)
		shouldExit <- true
	})

	for {
		select {
		case <-shouldExit:
			// time.Sleep(100 * time.Millisecond)
			return
		default:
			time.Sleep(100 * time.Millisecond)
		}
	}
}
