package com.example.clashroyale.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.clashroyale.utilits.Action;

public class NetStatusReceiver extends BroadcastReceiver {
    private Action mOnNetEnable = null;
    private Action mOnNetDisable = null;

    public void setOnNetEnable(Action onNetEnable) {
        mOnNetEnable = onNetEnable;
    }

    public void setOnNetDisable(Action onNetDisable) {
        mOnNetDisable = onNetDisable;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = wifi != null && wifi.isConnectedOrConnecting()
                || mobile != null && mobile.isConnectedOrConnecting();
        if (isConnected) {
            Log.d("NetStatusReceiver", "Network Available");
            if (mOnNetEnable != null)
                mOnNetEnable.execute();
        } else {
            Log.d("NetStatusReceiver", "Network Unavailable");
            if (mOnNetDisable != null)
                mOnNetDisable.execute();
        }
    }
}
