package com.soleilsplendide.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import org.json.JSONObject;


class PhoneCallStateListener extends PhoneStateListener {

  private String TAG;
  private CallbackContext context;

  public void setTAG(String tag) {
    this.TAG = tag;
  }

  public void setContext(CallbackContext context) {
    this.context = context;
  }

  public void onCallStateChanged(int state, String incomingNumber) {
    super.onCallStateChanged(state, incomingNumber);

    if (context != null) {
      String call_state = "";

      switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
          call_state = "IDLE";
          break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
          call_state = "OFFHOOK";
          break;
        case TelephonyManager.CALL_STATE_RINGING:
          call_state = "RINGING";
          break;
      }

      PluginResult result;

      try {
        JSONObject msgJSON = new JSONObject();
        msgJSON.put("state", call_state);
        msgJSON.put("number", incomingNumber);
        result = new PluginResult(PluginResult.Status.OK, msgJSON);
        LOG.d(TAG, msgJSON.toString());

      } catch (Exception e) {
        result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
        LOG.d(TAG, "JSON error!");
      }

      LOG.d(TAG, "Output!");
      result.setKeepCallback(true);
      context.sendPluginResult(result);
    }
  }
}
