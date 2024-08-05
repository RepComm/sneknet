package com.repcomm.sneknet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**Intent Broadcast receiver shit is too verbose, fixed it for ya*/
public class EZAction {
  public static void Fire(Context ctx, String action) {
    Intent intent = new Intent(action);
    ctx.sendBroadcast(intent);
  }
  public static void FireData(Context ctx, String action, String data) {
    Intent intent = new Intent(action);
    intent.putExtra("data", data);
    ctx.sendBroadcast(intent);
  }
  
  public interface EZActionListener {
    /**data may be null*/
    public void onAction(String action, String data);
  }
  
  /**Listen to actions using a broadcast receiver, but with less boilerplate*/
  public static BroadcastReceiver Listen(EZActionListener l, String[] actions, Context ctx) {
    BroadcastReceiver result = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) return;
        String data = intent.getStringExtra("data");
        l.onAction(action, data);
      }
    };
    IntentFilter f = new IntentFilter();
    for (String a : actions) {
      f.addAction(a);
    }
    ctx.registerReceiver(result, f);
    return result;
  }
  
  /**Unregisters broadcast receiver
   * If br is null, does nothing*/
  public static void Deafen(BroadcastReceiver br, Context ctx) {
    if (br == null) {
      return;
    }
    ctx.unregisterReceiver(br);
  }
}
