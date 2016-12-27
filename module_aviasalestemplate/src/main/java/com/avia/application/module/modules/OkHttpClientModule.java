package com.avia.application.module.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class OkHttpClientModule {
    public static final String KEY_PICASSO_OKHTTPCLIENT = "PICASSO_OKHTTPCLIENT";
    public static final String KEY_API_OKHTTPCLIENT = "API_OKHTTPCLIENT";
    
    @Provides @Named(KEY_PICASSO_OKHTTPCLIENT)
    @Singleton
    OkHttpClient providesPicassoOkHttpClient() {
        return new OkHttpClient();
    }

}