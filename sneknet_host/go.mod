module sneknet

go 1.22.3

require github.com/songgao/water v0.0.0-20200317203138-2b4b6d7c09d8

require google.golang.org/protobuf v1.34.2

require (
	github.com/gopacket/gopacket v1.2.0
	github.com/songgao/packets v0.0.0-20160404182456-549a10cd4091
	golang.org/x/sys v0.20.0 // indirect
)

//replace github.com/gopacket/gopacket v1.2.0 => ../../go/gopacket
replace github.com/gopacket/gopacket v1.2.0 => github.com/RepComm/gopacket v0.0.0-20240804172236-614dd94388ad
