package ru.aviasales.adsinterface;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;


public interface AdsInterface {
    void setWaitingScreenAdsEnabled(boolean waitingScreenAdsEnabled);

    void setStartAdsEnabled(boolean startAdsEnabled);

    void showStartAdsIfAvailable(final Activity activity);

    void setResultsAdsEnabled(boolean resultsAdsEnabled);

    void showWaitingScreenAdsIfAvailable(Activity activity);

    @Nullable
    View getMrecView(Activity activity);

    @Nullable
    View getNativeAdView(Activity activity);

    boolean isStartAdsEnabled();

    boolean isWaitingScreenAdsEnabled();

    boolean isResultsAdsEnabled();

    boolean areResultsReadyToShow();
}
