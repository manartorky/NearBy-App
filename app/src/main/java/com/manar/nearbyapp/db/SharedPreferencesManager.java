package com.manar.nearbyapp.db;

import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public static final String DEFAULT_APP_PREFS_NAME = "nearby-default-prefs";
    public static final String PREF_IS_REALTIME = "isRealtime";
    private SharedPreferences prefs;


    public SharedPreferencesManager(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void saveBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, true);
    }

}
