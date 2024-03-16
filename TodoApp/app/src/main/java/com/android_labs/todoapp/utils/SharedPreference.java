package com.android_labs.todoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String USER_PERFERNCE = "user_data";
    private SharedPreferences appShared;
    private SharedPreferences.Editor prefsEditor;

    public SharedPreference(Context ctx) {
        this.appShared = ctx.getSharedPreferences(USER_PERFERNCE, Activity.MODE_PRIVATE);
        this.prefsEditor = appShared.edit();
    }

    public void removeKey(String key) {
        this.prefsEditor.remove(key).commit();
    }

    public int getIntValue(String key) {
        return this.appShared.getInt(key, 0);
    }

    public void setIntValue(String key, int val) {
        this.prefsEditor.putInt(key, val).commit();
    }

    public String getStringValue(String key) {
        return this.appShared.getString(key, "");
    }

    public void setStringValue(String key, String val) {
        if (val.trim().isEmpty()) {
            return;
        }

        this.prefsEditor.putString(key, val).commit();
    }


    public boolean getBoolValue(String key) {
        return this.appShared.getBoolean(key, false);
    }

    public void setBoolValue(String key, boolean val) {
        this.prefsEditor.putBoolean(key, val).commit();
    }
}
