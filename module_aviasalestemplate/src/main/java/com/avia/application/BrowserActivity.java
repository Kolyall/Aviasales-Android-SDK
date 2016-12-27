package com.avia.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.avia.application.ui.fragment.BrowserFragment;


public class BrowserActivity extends AppCompatActivity {
	public static final String SHOW_LOADING_DIALOG = "show_loading_dialog";
	public static final String HOST = "HOST";
	private static final int FRAGMENT_CONTAINER = R.id.fragment_child_place;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aviasales_fragment_layout);
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		BrowserFragment fragment = BrowserFragment.newInstance(needToShowLoadingDialog(), getHost());
		fragmentTransaction.replace(FRAGMENT_CONTAINER, fragment, null);
		fragmentTransaction.commit();
	}

	private boolean needToShowLoadingDialog() {
		Intent intent = getIntent();
		return intent != null && intent.getExtras() != null && intent.getExtras().getBoolean(SHOW_LOADING_DIALOG, false);
	}

	private String getHost() {
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			return intent.getExtras().getString(HOST);
		}
		return null;
	}
}
