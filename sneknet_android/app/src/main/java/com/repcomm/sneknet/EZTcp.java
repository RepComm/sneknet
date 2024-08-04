package com.repcomm.sneknet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EZTcp {
  public interface Callbacks {
    void onConnect(EZTcp c);
    void onDisconnect(EZTcp c);
    void onException(EZTcp c, Exception e);
    void onRead(EZTcp c, byte[] data, int readsize);
  }
  Thread readThread;
  Socket s;
  OutputStream w;
  InputStream r;
  
  byte[] readBuffer;
  
  Callbacks callbacks;
  
  public EZTcp() {
  
  }
  
  private void onException(Exception e) {
    disconnect();
  }
  private void onConnect() {
    callbacks.onConnect(this);
  }
  private void onTick() {
    try {
      int rc = r.read(readBuffer);
      callbacks.onRead(this, readBuffer, rc);
    } catch (IOException ex) {
      onException(ex);
    }
  }
  private void onDisconnect() {
    callbacks.onDisconnect(this);
  }
  public void connect(InetSocketAddress addr, Callbacks c) {
    readThread = new Thread(()->{
      callbacks = c;
      if (callbacks == null) {
        onException(new IllegalArgumentException("callbacks cannot be null"));
      }
      if (s != null) {
        onException(new IllegalStateException("cannot connect, already connected"));
        return;
      }
      s = new Socket();
      try {
        s.bind(addr);
      } catch (IOException ex) {
        onException(ex);
        return;
      }
      try {
        w = s.getOutputStream();
      } catch (IOException ex) {
        onException(ex);
        return;
      }
      try {
        r = s.getInputStream();
      } catch (IOException ex) {
        onException(ex);
        return;
      }
      readBuffer = new byte[256];
      onConnect();
      while (!readThread.isInterrupted()) {
        onTick();
      }
      readBuffer = null;
      onDisconnect();
    });
  }
  
  /**Interrupts the socket thread, which in turn closes resources
   * Returns false if nothing to disconnect
   */
  public boolean disconnect() {
    if (readThread != null) {
      readThread.interrupt();
      return true;
    }
    return false;
  }
}
