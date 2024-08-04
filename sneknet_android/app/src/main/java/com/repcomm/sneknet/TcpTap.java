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
  private static final Object writeLock = new Object();
  static List<EZTcp> tcps = new ArrayList();
  
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
        
        final EZTcp c = new EZTcp();
        EZTcp.Callbacks cbs = new EZTcp.Callbacks() {
          @Override
          public void onConnect(EZTcp c) {
            synchronized (writeLock) {
              tcps.add(c);
            }
          }
          @Override
          public void onDisconnect(EZTcp c) {
            synchronized (writeLock) {
              tcps.remove(c);
            }
          }
          @Override
          public void onException(EZTcp c, Exception e) {
          
          }
          @Override
          public void onRead(EZTcp c, byte[] data, int readsize) {
            synchronized (writeLock) {
            
            }
          }
        };
        c.connect(dst, cbs);
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
