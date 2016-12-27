package com.avia.application.api.params;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import lombok.Getter;
import lombok.Setter;

public class AirlineLogoParams extends ApiParams {
    @Getter @Setter private String iata;
    @Getter @Setter private ImageView image;
    @Getter @Setter private ImageLoadingListener imageLoadingListener;
    @Getter @Setter private int width;
    @Getter @Setter private int height;
}
