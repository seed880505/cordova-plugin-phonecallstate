package com.soleilsplendide.cordova;

import android.content.pm.PackageManager;
import android.Manifest;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;
import org.apache.cordova.PermissionHelper;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import org.json.JSONArray;
import org.json.JSONException;


public class PhoneCallState extends CordovaPlugin {

  private String TAG = "PhoneCallStatePlugin";
  private CallbackContext context;
  private PhoneCallStateListener listener;
  private String[] permissions = {Manifest.permission.READ_PHONE_STATE};
  private static final int CODE_WATCH_STATE = 1;

  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    LOG.d(TAG, "Plugin execute!");
    context = callbackContext;

    if (action.equals("watchState")) {
      LOG.d(TAG, "Action watchState!");

      if (hasPermission()) {
        prepareWatchState();
        return true;
      } else {
        PermissionHelper.requestPermissions(this, CODE_WATCH_STATE, permissions);
      }
      return true;
    }
    return false;  // Returning false results in a "MethodNotFound" error.
  }

  private boolean hasPermission() {
    for (String p : permissions) {
      if (!PermissionHelper.hasPermission(this, p)) {
        LOG.d(TAG, "Has no permission!");
        return false;
      }
    }
    LOG.d(TAG, "Has all permission!");
    return true;
  }

  public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
    PluginResult result;

    if (context != null) {
      for (int r : grantResults) {
        if (r == PackageManager.PERMISSION_DENIED) {
          LOG.d(TAG, "Permission Denied!");

          result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
          context.sendPluginResult(result);
          return;
        }
      }

      switch (requestCode) {
        case CODE_WATCH_STATE:
          prepareWatchState();
          break;
      }
    }
  }

  private void prepareWatchState() {
    if (listener == null) {
      LOG.d(TAG, "Build state listener!");

      listener = new PhoneCallStateListener();
      listener.setTAG(TAG);
      listener.setContext(context);
      TelephonyManager TelephonyMgr = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
      TelephonyMgr.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }
  }

}
