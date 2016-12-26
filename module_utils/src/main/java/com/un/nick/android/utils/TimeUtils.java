package com.un.nick.android.utils;

import android.content.Context;

/**
 * Created by Nikolay Unuchek on 21.12.2016.
 */

public class TimeUtils {
    // Format: 00h 00m
    private static String getDefaultDurationString(Context context, Integer durationInMin) {
        String durationString = "";
        int hours = durationInMin / 60;
        int minutes = durationInMin % 60;
        String hoursStr;
        if (hours < 10) {
            hoursStr = "0" + hours;
        } else {
            hoursStr = String.valueOf(hours);
        }
        durationString += hoursStr + context.getString(R.string.hour_short) + " ";
        String minutesStr = String.valueOf(minutes);
        if (minutes < 10) {
            minutesStr = "0" + minutes;
        }
        durationString += minutesStr + context.getString(R.string.minute_short);
        return durationString;
    }
}
