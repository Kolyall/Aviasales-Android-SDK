package com.un.nick.android.utils;

import android.content.Context;

/**
 * Created by Nikolay Unuchek on 21.12.2016.
 */

public class DisplayUtils {
    public static int convertDPtoPixels(Context context, float dps) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
