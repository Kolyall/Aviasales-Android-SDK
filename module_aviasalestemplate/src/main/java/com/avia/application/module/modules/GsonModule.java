package com.avia.application.module.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class GsonModule {
    public static final String LOWER_CASE_WITH_UNDERSCORES = "LOWER_CASE_WITH_UNDERSCORES";

    @Provides
    @Singleton
    @Named(LOWER_CASE_WITH_UNDERSCORES)
    public Gson provideLowerCaseGson() {
        return build(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }

    private Gson build(FieldNamingPolicy fieldNamingPolicy) {
        return new GsonBuilder().setFieldNamingPolicy(fieldNamingPolicy).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    }
}