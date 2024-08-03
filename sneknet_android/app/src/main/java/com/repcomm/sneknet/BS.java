package com.repcomm.sneknet;

import java.util.ArrayList;
import java.util.List;

public class BS {
  public static interface BSCB {
    void onMsg(String tag, String msg);
  }
  static List<BSCB> cbs = new ArrayList<>();
  public static void send(String tag, String msg) {
    BS.cbs.forEach((cb)->{
      cb.onMsg(tag, msg);
    });
  }
  public static void listen(BSCB cb) {
    cbs.add(cb);
  }
  public static void deafen(BSCB cb) {
    cbs.remove(cb);
  }
}
