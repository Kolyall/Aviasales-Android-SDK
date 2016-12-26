package com.un.nick.android.utils;

import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

/**
 * Created by Nikolay Unuchek on 21.12.2016.
 */

public class StringUtils {
    public static String capitalizeFirstLetter(String original) {
        if (TextUtils.isEmpty(original))
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static SpannableString getSpannableString(CharSequence string, ParcelableSpan span) {
        SpannableString spannable = new SpannableString(string);
        if (span != null) {
            spannable.setSpan(span, 0, string.length(), 0);
        }
        return spannable;
    }
    // Return 1231231 rub
    public static SpannableStringBuilder getSpannablePriceString(String priceString, String currency) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(priceString);
        builder.append(" ");
        builder.append(getSpannableString(currency, new RelativeSizeSpan(0.4f)));
        return builder;
    }
}
