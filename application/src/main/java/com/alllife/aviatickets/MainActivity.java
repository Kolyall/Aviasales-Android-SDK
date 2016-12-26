package com.alllife.aviatickets;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.aviasales.core.AviasalesSDK;
import ru.aviasales.core.identification.IdentificationData;
import ru.aviasales.template.ui.fragment.AviasalesFragment;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int VEIW_LAYOUT = R.layout.activity_main;
	private static final int FRAGMENT_CONTAINER = R.id.fragment_place;
	private AviasalesFragment aviasalesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AviasalesSDK.getInstance().init(this, new IdentificationData(getString(R.string.travel_payouts_marker), getString(R.string.travel_payouts_token)));
		setContentView(VEIW_LAYOUT);

		init();
	}

	private void init() {
//		initAppodeal();
		initFragment();
	}

//	private final static String APPODEAL_APP_KEY = "your_travel_appodeal_key";
//	private final static boolean SHOW_ADS_ON_START = true;
//	private final static boolean SHOW_ADS_ON_WAITING_SCREEN = true;
//	private final static boolean SHOW_ADS_ON_SEARCH_RESULTS = true;
//	private void initAppodeal() {
//		AppodealAds ads = new AppodealAds();
//		Appodeal.setLogging(true);
//		ads.setStartAdsEnabled(SHOW_ADS_ON_START);
//		ads.setWaitingScreenAdsEnabled(SHOW_ADS_ON_WAITING_SCREEN);
//		ads.setResultsAdsEnabled(SHOW_ADS_ON_SEARCH_RESULTS);
//		ads.init(this, APPODEAL_APP_KEY);
//		AdsImplKeeper.getInstance().setCustomAdsInterfaceImpl(ads);
//	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		aviasalesFragment = (AviasalesFragment) fm.findFragmentByTag(AviasalesFragment.TAG);

		if (aviasalesFragment == null) {
			aviasalesFragment = (AviasalesFragment) AviasalesFragment.newInstance();
		}

		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(FRAGMENT_CONTAINER, aviasalesFragment, AviasalesFragment.TAG);
		fragmentTransaction.commit();
	}

	@Override
	public void onBackPressed() {
		if (!aviasalesFragment.onBackPressed()) {
			super.onBackPressed();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
