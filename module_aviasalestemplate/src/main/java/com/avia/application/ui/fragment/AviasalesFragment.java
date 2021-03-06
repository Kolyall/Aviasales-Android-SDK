package com.avia.application.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avia.application.R;
import com.avia.application.ads.AdsImplKeeper;
import com.avia.application.module.DependencyInjection;
import com.avia.application.ui.listener.AviasalesImpl;
import com.avia.application.ui.model.SearchFormData;
import com.avia.application.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.aviasales.core.search.params.SearchParams;
import ru.aviasales.core.search_airports.object.PlaceData;

public class AviasalesFragment extends Fragment implements AviasalesImpl {
	public static final String TAG = AviasalesFragment.class.getSimpleName();
	private static final int VIEW_LAYOUT = R.layout.aviasales_fragment_layout;

	private final static String TAG_CHILD = "aviasales_child_fragment";

	private static final String SEARCH_PARAMS_IS_UPDATED = "searh_params_is_updated";
	private final static int CACHE_SIZE = 20 * 1024 * 1024;
	private final static int CACHE_FILE_COUNT = 100;
	private final static int MEMORY_CACHE_SIZE = 5 * 1024 * 1024;
	private static final String AD_SHOWED_ONCE = "AD_SHOWED_ONCE";
	private static final int FRAGMENT_CONTAINER = R.id.fragment_child_place;

	private FragmentManager fragmentManager;

	private SearchFormData searchFormData;

	private View rootView;
	private boolean adShowedOnce = false;

	public static Fragment newInstance() {
		return new AviasalesFragment();
	}

	@Inject AdsImplKeeper mAdsImplKeeper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(AD_SHOWED_ONCE)) {
			adShowedOnce = savedInstanceState.getBoolean(AD_SHOWED_ONCE);
		}

		migrateSearchFormTripClassParam();

		searchFormData = new SearchFormData(getActivity().getApplicationContext());
		initImageLoader(getActivity().getApplicationContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(VIEW_LAYOUT, container, false);
		if (!adShowedOnce) {
			adShowedOnce = true;
			rootView.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (getActivity() != null) {
						DependencyInjection.inject(AviasalesFragment.this);
						mAdsImplKeeper.getAdsInterface().showStartAdsIfAvailable(getActivity());
					}
				}
			}, TimeUnit.SECONDS.toMillis(2));
		}
		return rootView;
	}

	private void migrateSearchFormTripClassParam() {
		SharedPreferences prefs = Utils.getPreferences(getActivity());

		if (!prefs.getBoolean(SEARCH_PARAMS_IS_UPDATED, false)) {
			prefs.edit().putBoolean(SEARCH_PARAMS_IS_UPDATED, true).apply();
			try {
				int tripClass = prefs.getInt(SearchParams.SEARCH_PARAM_TRIP_CLASS, -10);
				if (tripClass != -10) {
					prefs.edit().remove(SearchParams.SEARCH_PARAM_TRIP_CLASS).
							putString(SearchParams.SEARCH_PARAM_TRIP_CLASS, SearchParams.convertToNewTripClass(tripClass))
							.apply();
				}
			} catch (ClassCastException e) {
				//ignore class cast exception
			}
		}
	}

	public FragmentManager getAviasalesFragmentManager() {
		return fragmentManager;
	}

	public void startFragment(BaseFragment fragment, boolean shouldAddToBackStack) {

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(FRAGMENT_CONTAINER, fragment, fragment.getClass().getSimpleName());
		if (shouldAddToBackStack) {
			fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
		}
		fragmentTransaction.commit();

	}

	public void popFragmentFromBackStack() {
		onBackPressed();
	}


	private void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.diskCacheSize(CACHE_SIZE)
				.diskCacheFileCount(CACHE_FILE_COUNT)
				.memoryCacheSize(MEMORY_CACHE_SIZE)
				.build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		fragmentManager = this.getChildFragmentManager();

		Fragment fragment;
		if ((fragmentManager.findFragmentByTag(TAG_CHILD)) == null) {
			fragment = SearchFormFragment.newInstance();
			fragmentManager.beginTransaction().replace(FRAGMENT_CONTAINER, fragment, TAG_CHILD).commit();
		}

	}

	public boolean onBackPressed() {
		if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0 && isVisible()) {
			fragmentManager.popBackStack();
			return true;
		} else {
			return false;
		}
	}

	public void popBackStackInclusive(String tag) {
		fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	@Override
	public void onStop() {
		searchFormData.saveState();
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (rootView != null) {
			ViewGroup parentViewGroup = (ViewGroup) rootView.getParent();
			if (parentViewGroup != null) {
				parentViewGroup.removeAllViews();
			}
		}
	}

	@Override
	public void onAirportSelected(PlaceData placeData, int typeOfDate, int segment, boolean isComplex) {
		if (isComplex) {
			if (typeOfDate == SelectAirportFragment.TYPE_ORIGIN) {
				getSearchFormData().getComplexSearchSegments().get(segment).setOrigin(placeData);
			} else {
				getSearchFormData().getComplexSearchSegments().get(segment).setDestination(placeData);
			}
		} else {
			if (typeOfDate == SelectAirportFragment.TYPE_ORIGIN) {
				getSearchFormData().getSimpleSearchParams().setOrigin(placeData);
			} else {
				getSearchFormData().getSimpleSearchParams().setDestination(placeData);
			}
		}
	}

	@Override
	public SearchFormData getSearchFormData() {
		return searchFormData;
	}

	@Override
	public void saveState() {
		searchFormData.saveState();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(AD_SHOWED_ONCE, adShowedOnce);
		super.onSaveInstanceState(outState);
	}
}
