package com.example.jimappv2a.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_NUM_DNI= "numdni";
    static final String PREF_CURSO_ID= "cursoid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }



    public static void setDatos(Context ctx, String numdni, String cursoid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        //editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_NUM_DNI, numdni);
        editor.putString(PREF_CURSO_ID, cursoid);
        editor.apply();
    }

    public static String getDatos(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NUM_DNI, "");
    }

    public static void clearDatos(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.apply();
    }
}
