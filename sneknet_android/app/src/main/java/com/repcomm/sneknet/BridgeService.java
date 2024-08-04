package com.repcomm.sneknet;

import static com.repcomm.sneknet.Utils.BAReadUint32;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.ServiceCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.repcomm.sneknet.protos.IPC;
import com.repcomm.sneknet.protos.IPv4TcpMsg;
import com.repcomm.sneknet.protos.IpcMsg;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BridgeService extends Service {
  private static final String CHANNEL_ID = "my_foreground_service_channel";
  
//  ServerSocket ss;
  Thread st;
  byte[] prefixBuffer;
  byte[] readBuffer;
  
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
  
  public void logExceptionMsg(Exception e) {
    BS.send("exception", e.toString());
  }
  public void logOutputMsg(String msg) {
    BS.send("output", msg);
  }
  
  void handleAccept(Selector selector, ServerSocketChannel ch, SelectionKey k) {
    SocketChannel client;
    try {
      client = ch.accept();
      client.configureBlocking(false);
      client.register(selector, SelectionKey.OP_READ);
    } catch (IOException ex) {
      logExceptionMsg(ex);
      return;
    }
    logOutputMsg("BRIDGE: connection");
  }
  /**Returns true when disconnect detected*/
  public static boolean readN(SocketChannel socketChannel, int n, byte[] output) throws IOException {
    int totalBytesRead = 0;
    final int MIN_BUFFER_SIZE = 256;
    
    while (totalBytesRead < n) {
      ByteBuffer buffer = ByteBuffer.allocate(Math.min(n - totalBytesRead, MIN_BUFFER_SIZE));
      int bytesRead = socketChannel.read(buffer);
      if (bytesRead == -1) {
        return true;
      }
      buffer.flip();
      buffer.get(output, totalBytesRead, bytesRead);
      totalBytesRead += bytesRead;
    }
    return false;
  }
  
  /**Returns true when disconnect detected*/
  boolean handleRead(SelectionKey k) throws Exception {
    SocketChannel client = (SocketChannel)k.channel();
    
    if (readN(client, 4, prefixBuffer)) {
      return true; //client disconnect
    }
    int dataLen = BAReadUint32(prefixBuffer);
    
//    if (readBuffer.length < dataLen) {
      readBuffer = new byte[dataLen];
//    }
    if (readN(client, dataLen, readBuffer)) {
      readBuffer = null;
      return true; //client disconnect
    }
    
//    logOutputMsg("read " + (dataLen + 4) + " bytes");
    
    IpcMsg m;
    try {
      m = IpcMsg.parseFrom(readBuffer);
    } catch (Exception ex) {
      System.err.println(ex.toString());
      logExceptionMsg(ex);
      return false;
    }
    switch (m.getPayloadCase()) {
      case IPV4TCP:
        TcpTap.Handle(m.getIPv4Tcp());
        break;
    }
    return false;
  }
  
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    logOutputMsg("BRIDGE: started service");
    
    createNotificationChannel();
    Notification notification = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      notification = new Notification.Builder(this, CHANNEL_ID)
        .setContentTitle("sneknet bridge")
            .setContentText("running")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build();
    }
    startForeground(1, notification);
    
//    readBuffer = ByteBuffer.allocate(4096);
    prefixBuffer = new byte[4];
    st = new Thread(()->{
      
      Selector selector;
      ServerSocketChannel ch;
      ServerSocket ss;
      
      try {
        selector = Selector.open();
        ch = ServerSocketChannel.open();
        ss = ch.socket();
        ss.bind(new InetSocketAddress("localhost", 10209));
        ch.configureBlocking(false);
        int ops = ch.validOps();
        ch.register(selector, ops, null);
      } catch (IOException e) {
        logExceptionMsg(e);
        return;
      }
      
      while (!Thread.interrupted()) {
        try {
          selector.select();
        } catch (IOException e) {
          logExceptionMsg(e);
          continue;
        }
        Set<SelectionKey> ks = selector.selectedKeys();
        Iterator<SelectionKey> i = ks.iterator();
        
        while (i.hasNext() && !Thread.interrupted()) {
          SelectionKey k = i.next();
          if (k.isAcceptable()) {
            handleAccept(selector, ch, k);
          } else if (k.isReadable()) {
            boolean disconnect = false;
            try {
              disconnect = handleRead(k);
            } catch (Exception e) {
              logExceptionMsg(e);
              k.cancel();
              continue;
            }
            if (disconnect) {
              k.cancel();
              logOutputMsg("BRIDGE: disconnected");
              continue;
            }
          }
          i.remove();
        }
        
      }
      logOutputMsg("BRIDGE: thread interrupted for shutdown");
      try {
        ss.close();
      } catch (IOException e) {
        logExceptionMsg(e);
      }
    });
    
    logOutputMsg("BRIDGE: starting bridge thread");
    st.start();
    logOutputMsg("BRIDGE: started bridge thread");
    
    return START_STICKY;
  }
  
  @Override
  public void onDestroy() {
    if (st != null) {
      
      st.interrupt();
      readBuffer = null;
      st = null;
    }
    logOutputMsg("BRIDGE: stopped");
  }
  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(
        CHANNEL_ID,
        "sneknet bridge channel",
        NotificationManager.IMPORTANCE_DEFAULT
      );
      
      NotificationManager manager = getSystemService(NotificationManager.class);
      manager.createNotificationChannel(channel);
      
    }
  }
  
}