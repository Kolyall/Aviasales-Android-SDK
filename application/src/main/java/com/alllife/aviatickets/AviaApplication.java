package com.alllife.aviatickets;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.avia.application.TheApplication;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Nikolay Unuchek on 26.12.2016.
 */

public class AviaApplication extends TheApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Crashlytics crashlytics = new Crashlytics();
            Fabric.with(this, crashlytics);
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
