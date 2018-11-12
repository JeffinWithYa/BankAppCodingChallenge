package com.jjeyachandren.manulifechallenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {

    static final String PREF_USER_STATUS= "userStatus";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setStatus(Context ctx, String status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_STATUS, status);
        editor.commit();
    }

    public static String getUserStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_STATUS, "");
    }

    public static void clearUserStatus(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
