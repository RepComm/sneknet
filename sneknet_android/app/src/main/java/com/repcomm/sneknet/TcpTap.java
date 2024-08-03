package com.repcomm.sneknet;

import static com.repcomm.sneknet.Utils.BBReadUint16;
import static com.repcomm.sneknet.Utils.IntToIPv4Addr;

import com.repcomm.sneknet.protos.IPv4Info;
import com.repcomm.sneknet.protos.IPv4TcpMsg;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TcpTap {
  static List<NIOTcpClient> tcps = new ArrayList();
  
  public static void Handle(IPv4TcpMsg m) {
    int srcPort = m.getSrcPort();
    int dstPort = m.getDstPort();
    IPv4Info info = m.getIPv4();
    
    String srcIpv4 = IntToIPv4Addr(info.getSrcIPv4());
    String dstIpv4 = IntToIPv4Addr(info.getDstIPv4());
    
    if (m.getIsSyn()) {
      if (m.getIsAck()) { // INIT(2) client <- server
        System.out.printf(
          "INIT(2) client (%d) <- server (%d) \n",
          srcPort,
          dstPort
        );
      } else { //INIT(1) client -> server
        System.out.printf(
          "INIT(1) client (%d) -> server (%d) \n",
          srcPort,
          dstPort
        );
        
        InetSocketAddress dst = new InetSocketAddress(
          dstIpv4,
          dstPort
        );
        
        System.out.print("connecting to ");
        System.out.println(dst);
        
        //create a TCP client
//        NIOTcpClient c = new NIOTcpClient() {
//          @Override
//          protected void onRead(ByteBuffer buf) throws Exception {
//
//          }
//
//          @Override
//          protected void onConnected() throws Exception {
//
//          }
//
//          @Override
//          protected void onDisconnected() {
//
//          }
//        };
//
//        c.setAddress(dst);
//        tcps.add(c);
//        try {
//          c.start();
//        } catch (IOException e) {
//          System.err.println(e.toString());
//        }
      }
    } else { //INIT(3) client -> server
      System.out.printf(
        "INIT(3) client (%d) -> server (%d) \n",
        srcPort,
        dstPort
      );
    }
  }
}
