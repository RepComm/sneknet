package com.repcomm.sneknet;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {
  TextView errOutput;
  TextView logOutput;
  
  Context bridgeContext;
  Intent bridgeIntent;
  void startBridge() {
    bridgeContext = getApplicationContext();
    bridgeIntent = new Intent(this, BridgeService.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      bridgeContext.startForegroundService(bridgeIntent);
    }
  }
  
  void stopBridge() {
    bridgeContext.stopService(bridgeIntent);
    bridgeContext = null;
    bridgeIntent = null;
  }
  
  public void logExceptionMsg(String msg) {
    errOutput.setText(
      errOutput.getText() + "\n" + msg
    );
    final int scrollAmount = errOutput.getLayout()
      .getLineTop(errOutput.getLineCount()) - errOutput.getHeight();
    errOutput.scrollTo(0, scrollAmount);
  }
  public void logOutputMsg(String msg) {
//    System.out.println(msg);
    logOutput.setText(
      logOutput.getText() + "\n" + msg
    );
    final int scrollAmount = logOutput.getLayout()
      .getLineTop(logOutput.getLineCount()) - logOutput.getHeight();
    logOutput.scrollTo(0, scrollAmount);
  }
  
  BS.BSCB bscb;
  
  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    if (bscb != null) {
      BS.deafen(bscb);
      bscb = null;
    }
    bscb = (String tag, String msg)->{
      this.runOnUiThread(()->{
        
        switch (tag) {
          case "output":
            logOutputMsg(msg);
            break;
          case "exception":
            logExceptionMsg(msg);
            break;
        }
        
      });
    };
    BS.listen(bscb);
    
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    
    
    errOutput = findViewById(R.id.errOutput);
    errOutput.setMovementMethod(new ScrollingMovementMethod());
    
    logOutput = findViewById(R.id.logOutput);
    logOutput.setMovementMethod(new ScrollingMovementMethod());
    
    Switch swBridge = findViewById(R.id.swBridge);
    swBridge.setOnCheckedChangeListener((bv, isChecked) -> {
      
      if (isChecked && bridgeContext == null) {
        startBridge();
      } else {
        stopBridge();
      }
    });
    
  }
}