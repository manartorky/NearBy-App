package com.manar.nearbyapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.manar.nearbyapp.NearByApplication;


public class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) NearByApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}