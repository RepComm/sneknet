package com.repcomm.sneknet;

import android.annotation.SuppressLint;

import java.nio.ByteBuffer;

public class Utils {
  @SuppressLint("DefaultLocale")
  public static String IntToIPv4Addr (int ip) {
    return String.format("%d.%d.%d.%d",
      (ip >> 24 & 0xff),
      (ip >> 16 & 0xff),
      (ip >> 8 & 0xff),
      (ip & 0xff)
    );
      
  }
  public static String BBReadIPv4(ByteBuffer b) {
    StringBuilder sb = new StringBuilder();
    sb.append(b.get() & 0xFF);
    sb.append(".");
    sb.append(b.get() & 0xFF);
    sb.append(".");
    sb.append(b.get() & 0xFF);
    sb.append(".");
    sb.append(b.get() & 0xFF);
    return sb.toString();
  }
  
  public static int BBReadUint16(ByteBuffer b) {
    int value = 0;
    value |= (b.get() & 0xFF) << 8;
    value |= b.get() & 0xFF;
    return value & 0xFFFF;
  }
  public static long BBReadUint64(ByteBuffer b) {
    byte A = b.get();
    byte B = b.get();
    byte C = b.get();
    byte D = b.get();
    byte E = b.get();
    byte F = b.get();
    byte G = b.get();
    byte H = b.get();
    return ((long) H << 56) +
      ((long) G << 48) +
      ((long) F << 40) +
      ((long) E << 32) +
      ((long) D << 24) +
      ((long) C << 16) +
      ((long) B <<  8) +
      (long)  A;
  }
  
}
