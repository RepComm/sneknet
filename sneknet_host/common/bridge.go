package common

import (
	"bytes"
	"encoding/binary"
	"fmt"
	"log"
	"net"
	"os"
	"sneknet/common/protos"

	"github.com/gopacket/gopacket/layers"
	"google.golang.org/protobuf/proto"
	"google.golang.org/protobuf/reflect/protoreflect"
)

type Bridge struct {
	OnFatal func(err error)
	port    uint16
	conn    net.Conn
	OnData  func(data []byte, len int)
	done    chan bool
}

func NewBridge(port uint16) *Bridge {
	result := new(Bridge)
	result.port = port
	result.done = make(chan bool, 1)
	return result
}

func (b *Bridge) Start() error {
	androidBridgePort := 10209
	AdbPath := os.Getenv("ADB_PATH")
	if AdbPath == "" {
		fmt.Println("ADB_PATH env var not set, defaulting to 'adb'")
		AdbPath = "adb"
	}
	fmt.Println("running adb from: ", AdbPath)
	adb := ADB{
		AdbPath: AdbPath,
	}
	err := adb.Forward(androidBridgePort, androidBridgePort)

	if err != nil {
		fmt.Println("couldn't forward port via 'adb forward'")
		fmt.Println(err)
		b.OnFatal(err)
		return err
	}

	//TCP for now, UDP only available via hacks, thanks adb
	conn, err := net.Dial("tcp", fmt.Sprintf("127.0.0.1:%d", b.port))
	if err != nil {
		fmt.Println("couldn't establish UDP to android app")
		b.OnFatal(err)
		return err
	}
	b.conn = conn

	go b.ReadLoop()
	return nil
}

/**Writes data to bridge, but with a uint32 prefix for payload size*/
func (b *Bridge) WritePrefixedBuffer(data []byte) (n int, err error) {
	dataLen := len(data)
	prefixLen := 4

	prefix := make([]byte, prefixLen)

	binary.LittleEndian.PutUint32(prefix, uint32(dataLen))
	b.conn.Write(prefix) //write a prefix so java can anticipate alloc size
	return b.conn.Write(data)
}
func (b *Bridge) WriteProto(m protoreflect.ProtoMessage) (err error) {
	buf, err := proto.Marshal(m)
	if err != nil {
		return err
	}
	_, err = b.WritePrefixedBuffer(buf)
	return err
}

func (b *Bridge) ReadLoop() {
	p := make([]byte, 2048)

	for {
		select {
		case <-b.done:
			log.Println("bridge: closing")

			b.conn.Close()
			b.conn = nil
			log.Println("bridge: closed")
			return
		default:
			readCount, err := b.conn.Read(p)
			if err != nil {
				b.OnFatal(err)
				b.done <- true
				continue
			}
			if b.OnData != nil {
				b.OnData(p, readCount)
			}

		}
	}
}

func (b *Bridge) Close() {
	b.done <- true
}

func (b *Bridge) HandleTCP(pIPv4 *layers.IPv4, pTCP *layers.TCP) {
	IsResponse := false
	IsSuccess := false
	r := bytes.NewReader(pIPv4.SrcIP.To4())
	var SrcIPv4_int int32
	var DstIPv4_int int32
	binary.Read(r, binary.LittleEndian, &SrcIPv4_int)
	r.Reset(pIPv4.DstIP.To4())
	binary.Read(r, binary.LittleEndian, &DstIPv4_int)

	//re-encoding seems silly
	//but this essentially gives us
	//a parser on java-side, and enables other communication easily
	//so it's worth it, cause i'm lazy
	ipcMsg := &protos.IpcMsg{
		TransactionId: 1,
		IsResponse:    &IsResponse,
		IsSuccess:     &IsSuccess,
		Error:         nil,
		Payload: &protos.IpcMsg_IPv4Tcp{
			IPv4Tcp: &protos.IPv4TcpMsg{
				IPv4: &protos.IPv4Info{
					SrcIPv4: SrcIPv4_int,
					DstIPv4: DstIPv4_int,
				},
				SrcPort: int32(pTCP.SrcPort),
				DstPort: int32(pTCP.DstPort),
				IsSyn:   pTCP.SYN,
				IsAck:   pTCP.ACK,
				SeqId:   pTCP.Seq,
				Payload: pTCP.Payload,
			},
		},
	}

	log.Println("TCP: ->", pIPv4.DstIP, pTCP.DstPort)

	err := b.WriteProto(ipcMsg)
	if err != nil {
		fmt.Println(err)
	}
}

func (b *Bridge) HandleICMPv4(pIPv4 *layers.IPv4, pICMPv4 *layers.ICMPv4) {
	icmpType := pICMPv4.TypeCode.Type()

	switch icmpType {
	case layers.ICMPv4TypeEchoRequest:
		log.Printf("ICMP: ping req %s -> %s", pIPv4.SrcIP, pIPv4.DstIP)
	case layers.ICMPv4TypeEchoReply:
		log.Printf("ICMP: ping res %s <- %s", pIPv4.SrcIP, pIPv4.DstIP)
	default:
		log.Println("ICMP: unhandle type", icmpType)
	}
}
