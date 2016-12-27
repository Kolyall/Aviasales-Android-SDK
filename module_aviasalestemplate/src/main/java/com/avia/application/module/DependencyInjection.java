package com.avia.application.module;
import com.avia.application.TheApplication;
import com.avia.application.ui.adapter.AdAdapter;
import com.avia.application.ui.fragment.AviasalesFragment;
import com.avia.application.ui.fragment.ResultsFragment;
import com.avia.application.ui.fragment.SearchingFragment;


public class DependencyInjection {

    public static void inject(TheApplication obj) {
        TheApplication.get().getComponent().inject(obj);
    }
    public static void inject(AdAdapter.AppodealAdViewHolder obj) {
        TheApplication.get().getComponent().inject(obj);
    }

    public static void inject(AviasalesFragment obj) {
        TheApplication.get().getComponent().inject(obj);
    }

    public static void inject(ResultsFragment obj) {
        TheApplication.get().getComponent().inject(obj);
    }

    public static void inject(SearchingFragment obj) {
        TheApplication.get().getComponent().inject(obj);
    }
}
