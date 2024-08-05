package com.repcomm.sneknet;

import static com.repcomm.sneknet.Utils.IsServiceRunning;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
  TextView errOutput;
  TextView logOutput;
  
  Context bridgeContext;
  Intent bridgeIntent;
  
  public void logExceptionMsg(String msg) {
    errOutput.setText(
      errOutput.getText() + "\n" + msg
    );
    Layout l = errOutput.getLayout();
    if (l != null) { //i swear to god android
      final int scrollAmount = errOutput.getLayout()
        .getLineTop(errOutput.getLineCount()) - errOutput.getHeight();
      errOutput.scrollTo(0, scrollAmount);
    }  }
  public void logOutputMsg(String msg) {
//    System.out.println(msg);
    logOutput.setText(
      logOutput.getText() + "\n" + msg
    );
    Layout l = logOutput.getLayout();
    if (l != null) { //i swear to god android
      final int scrollAmount = logOutput.getLayout()
        .getLineTop(logOutput.getLineCount()) - logOutput.getHeight();
      logOutput.scrollTo(0, scrollAmount);
    }
  }
  
  BroadcastReceiver bridgeListener;
  
  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
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
    //use on click instead of on changed so we can setChecked without looping accidentally
    swBridge.setOnClickListener((View v) ->{
      if (swBridge.isChecked()) {
        EZAction.FireData(this, "bridge_state", "start");
      } else {
        EZAction.FireData(this, "bridge_state", "stop");
      }
    });
    
    if (IsServiceRunning(this ,BridgeService.class)) {
      EZAction.FireData(this, "bridge_state", "get");
    } else {
      bridgeContext = getApplicationContext();
      bridgeIntent = new Intent(this, BridgeService.class);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        bridgeContext.startForegroundService(bridgeIntent);
      }
    }
    
    bridgeListener = EZAction.Listen((String action, String data)->{
      switch(action) {
        case "bridge_state":
          if (data != null) {
            //update the switch even when other services/activities modify the bridge state
            switch (data) {
              case "start":
              case "started":
                swBridge.setChecked(true);
                break;
              case "stop":
              case "stopped":
                swBridge.setChecked(false);
                break;
              case "exit":
                finish();
                break;
            }
            swBridge.setChecked(
              data.equals("start") ||
              data.equals("started")
            );
          }
          break;
        case "output":
          if (data != null) logOutputMsg(data);
          break;
        case "exception":
          if (data != null) logExceptionMsg(data);
          break;
      }
    }, new String[]{
      "bridge_state",
      "output",
      "exception"
    }, this);
    System.out.println("WTF");
    logOutputMsg("EZAction -> listening");
    
  }
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    EZAction.Deafen(bridgeListener, this);
    bridgeListener = null;
  }
}