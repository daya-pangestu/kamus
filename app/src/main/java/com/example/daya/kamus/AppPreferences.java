package com.example.daya.kamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class AppPreferences {

    private SharedPreferences sharedPreferences;
    private Context context;

    private static final String FIRST_RUN = "first_run";

    AppPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    void setFirstRun() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.apply();
    }

    Boolean getFirstRun() {
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }
}
