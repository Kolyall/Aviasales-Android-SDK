package com.un.nick.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;

public class BrowserUtils {

	public static final String URL = "BROWSER_URL";
	public static final String REFERER_HEADER = "Referer";
	public static final String HTTP = "http://";

	public static void openBrowser(@Nullable Activity activity,
	                               @Nullable String url,
	                               @Nullable String title,
	                               @Nullable String host,Class cls) {
		if (url == null || title == null) return;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			launchInternalBrowser(activity, cls, url);
		} else {
			openExternalBrowser(activity, url, host);
		}
	}

	public static void openExternalBrowser(@Nullable Activity activity, @Nullable String url, @Nullable String host) {
		if (activity == null) return;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		if (host != null) {
			Bundle bundle = new Bundle();
			bundle.putString(REFERER_HEADER, HTTP + host);
			intent.putExtra(Browser.EXTRA_HEADERS, bundle);
		}
		activity.startActivity(intent);
	}

	private static void launchInternalBrowser(Activity activity,Class cls, String url) {
		Intent intent = new Intent(activity, cls);
		intent.putExtra(URL, url);
		activity.startActivity(intent);
	}
}
