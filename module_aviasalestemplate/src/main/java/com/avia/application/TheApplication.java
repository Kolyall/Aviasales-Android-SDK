package com.avia.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.avia.application.module.components.AppComponent;
import com.avia.application.module.components.DaggerAppComponent;
import com.avia.application.module.modules.AppModule;

/**
 * Created by Nikolay Unuchek on 27.12.2016.
 */

public class TheApplication extends MultiDexApplication {
    private static Context context;
    private AppComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        setupComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }

    private void setupComponent() {
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        component.inject(this);
    }

    public AppComponent getComponent() {
        if (component==null)
            setupComponent();
        return component;
    }

    public static AppComponent getComponent(Context context) {
        return ((TheApplication) context.getApplicationContext()).getComponent();
    }

    public static TheApplication get() {
        return (TheApplication)getContext().getApplicationContext();
    }
}
