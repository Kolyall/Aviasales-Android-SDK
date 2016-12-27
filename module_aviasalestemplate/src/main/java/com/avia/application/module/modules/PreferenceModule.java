package com.avia.application.module.modules;

import android.content.Context;

import dagger.Module;

@Module
public class PreferenceModule {

    public static final String PREF_PUSH = "pref_catalog";

    public static void clear(Context context) {
        context.getApplicationContext()
            .getSharedPreferences(AppModule.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply();
    }
}
