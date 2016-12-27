package com.avia.application.ads;

import lombok.Getter;
import ru.aviasales.adsinterface.AdsInterface;

public class AdsImplKeeper {
    private static volatile AdsImplKeeper instance;
    @Getter private AdsInterface adsInterface = new AdsEmptyImpl();

    public void setCustomAdsInterfaceImpl(AdsInterface adsInterfaceImpl) {
        this.adsInterface = adsInterfaceImpl;
    }

}
