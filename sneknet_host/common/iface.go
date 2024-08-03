package common

import (
	"encoding/json"
	"fmt"
	"log"
	"os/exec"
	"strings"

	"github.com/songgao/packets/ethernet"
	"github.com/songgao/water"
)

type Iface struct {
	device  *water.Interface
	config  water.Config
	OnError func(err error)
	OnFrame func(frame ethernet.Frame)
	done    chan bool
}

func NewIface(ifname string) (*Iface, error) {
	config := water.Config{
		DeviceType: water.TAP,
		PlatformSpecificParams: water.PlatformSpecificParams{
			Name:    ifname,
			Persist: false,
		},
	}

	ifce, err := water.New(config)
	if err != nil {
		return nil, err
	}

	result := new(Iface)
	result.config = config
	result.device = ifce

	done := make(chan bool, 1)
	result.done = done

	return result, nil
}

func DeleteIface(ifname string) error {
	c := exec.Command("ip",
		"link",
		"delete",
		ifname,
	)
	return c.Run()
}

type IfaceJson struct {
	Name                string   `json:"ifname"`
	Flags               []string `json:"flags"`
	MaxTransferUnit     int      `json:"mtu"`
	QueueingDiscipline  string   `json:"qdisc"`
	State               string   `json:"operstate"`
	LinkMode            string   `json:"linkmode"`
	Group               string   `json:"group"`
	TransmitQueueLength int      `json:"txqlen"`
	LinkType            string   `json:"link_type"`
	Address             string   `json:"address"`
	Broadcast           string   `json:"broadcast"`
}

func ListIfaces() ([]IfaceJson, error) {
	c := exec.Command("ip",
		"--json",
		"link",
		"show",
	)
	o, err := c.Output()
	if err != nil {
		return nil, err
	}
	v := []IfaceJson{}
	err = json.Unmarshal(o, &v)
	if err != nil {
		return nil, err
	}
	return v, nil
}

func IfaceExists(ifname string) (bool, error) {
	ifaces, err := ListIfaces()
	if err != nil {
		return false, err
	}
	for _, iface := range ifaces {
		if strings.ToLower(iface.Name) == strings.ToLower(ifname) {
			return true, nil
		}
	}
	return false, nil
}

func (i *Iface) ReadLoop() {
	var frame ethernet.Frame

	if i.OnFrame == nil {
		panic("no OnFrame callback supplied to Iface, cannot read frames")
	}

	for {
		select {
		case <-i.done:
			log.Println("closing iface", i.config.Name)
			i.device.Close()
			i.device = nil
			log.Println("closed tap", i.config.Name)

			return
		default:
			frame.Resize(1500)
			n, err := i.device.Read([]byte(frame))

			if err != nil {
				if i.OnError != nil {
					i.OnError(err)
				} else {
					panic(err)
				}
			}
			frame = frame[:n]
			i.OnFrame(frame)

		}
	}

}

func (i *Iface) Start() {
	go i.ReadLoop()
}

func (i *Iface) Write(p []byte) (n int, err error) {
	return i.device.Write(p)
}

func (i *Iface) SetCIDR(ipv4addr string, netmask string) error {
	c := exec.Command("sudo", "ip", "addr", "add", fmt.Sprintf("%s/%s", ipv4addr, netmask), "dev", i.config.Name)
	o, err := c.Output()
	if err != nil {
		fmt.Println(string(o))
		return err
	}
	return nil
}

func (i *Iface) AllowIpv4Forward() error {
	// sysctl -w net.ipv4.ip_forward=1
	c := exec.Command("sudo", "sysctl", "-w", "net.ipv4.ip_forward=1")
	o, err := c.Output()
	if err != nil {
		fmt.Println(string(o))
		return err
	}
	return nil
}

func (i *Iface) AddRoute(defaultGatewayAddr string) error {
	c := exec.Command(
		"sudo", "ip",
		"route", "add",
		fmt.Sprintf("%s/%s", defaultGatewayAddr, "32"),
		"dev", i.config.Name,
	)
	log.Println("exec: ", c.String())
	_, err := c.Output()
	if err != nil {
		return err
	}
	return nil
}
func (i *Iface) AddDefaultGateway(defaultGatewayAddr string) error {
	// sudo ip route add default via <gateway_ip> dev <interface_name>
	c := exec.Command(
		"sudo", "ip",
		"route", "add", "default", "via",
		defaultGatewayAddr,
		"dev", i.config.Name,
	)
	log.Println("exec: ", c.String())
	_, err := c.Output()
	if err != nil {
		return err
	}
	return nil
}

func (i *Iface) Up() error {
	c := exec.Command("sudo", "ip", "link", "set", i.config.Name, "up")
	o, err := c.Output()
	if err != nil {
		log.Println(string(o))
		return err
	}
	return nil
}

func (i *Iface) Down() error {
	c := exec.Command("sudo", "ip", "link", "set", i.config.Name, "down")
	o, err := c.Output()
	if err != nil {
		log.Println(string(o))
		return err
	}
	return nil
}

func (i *Iface) Close() {
	i.done <- true
}
