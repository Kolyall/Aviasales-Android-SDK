package com.avia.application.module.components;

import com.avia.application.TheApplication;
import com.avia.application.module.annotations.ForApplication;
import com.avia.application.module.modules.AppModule;
import com.avia.application.module.modules.GsonModule;
import com.avia.application.module.modules.OkHttpClientModule;
import com.avia.application.module.modules.PreferenceModule;
import com.avia.application.ui.adapter.AdAdapter;
import com.avia.application.ui.fragment.AviasalesFragment;
import com.avia.application.ui.fragment.ResultsFragment;
import com.avia.application.ui.fragment.SearchingFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nikolay Unuchek on 07.09.2016.
 */
@ForApplication
@Singleton
@Component(
        modules = {AppModule.class,
                OkHttpClientModule.class,
                PreferenceModule.class,
                GsonModule.class
        })
public interface AppComponent {


    void inject(TheApplication obj);

    void inject(AdAdapter.AppodealAdViewHolder obj);

    void inject(AviasalesFragment obj);

    void inject(ResultsFragment obj);

    void inject(SearchingFragment obj);
}