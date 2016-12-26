package ru.aviasales.template.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eralp.circleprogressview.CircleProgressView;

import ru.aviasales.adsinterface.AdsInterface;
import ru.aviasales.core.AviasalesSDK;
import ru.aviasales.core.http.exception.ApiExceptions;
import ru.aviasales.core.search.object.SearchData;
import ru.aviasales.core.search.searching.SearchListener;
import ru.aviasales.template.R;
import ru.aviasales.template.ads.AdsImplKeeper;
import ru.aviasales.template.filters.manager.FiltersManager;
import ru.aviasales.template.utils.SortUtils;

public class SearchingFragment extends BaseFragment {

	private static final int VIEW_LAYOUT = R.layout.searching_fragment;
	public static final int ANIMATION_FINISH_DURATION = 1000;
	public static final int PROGRESS_BAR_LENGTH = 1000;

	private LinearLayout mrecContainer;
	private CircleProgressView mCircleProgressView;
	private boolean isPaused = false;

	public static SearchingFragment newInstance() {
		return new SearchingFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(VIEW_LAYOUT, container, false);

		setupViews(rootView);
		showActionBar(true);
		setTextToActionBar(getString(R.string.searching_information));
		setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		setUpMrecAd();

		return rootView;
	}

	private void setUpMrecAd() {
		AdsInterface adsInterface = AdsImplKeeper.getInstance().getAdsInterface();
		View mrecView = adsInterface.getMrecView(getActivity());
		if (mrecView != null) {
			mrecContainer.addView(mrecView);
			adsInterface.showWaitingScreenAdsIfAvailable(getActivity());
		}
	}

	private void setupViews(View rootView) {
		mCircleProgressView = (CircleProgressView) rootView.findViewById(R.id.circle_progress_view);
		mrecContainer = (LinearLayout) rootView.findViewById(R.id.mrec_container);

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		switch (AviasalesSDK.getInstance().getSearchingTicketsStatus()) {
			case SEARCHING:
				AviasalesSDK.getInstance().setOnTicketsSearchListener(new SearchListener() {
					@Override
					public void onSuccess(SearchData searchData) {
						SortUtils.resetSavedSortingType();
						FiltersManager.getInstance().initFilter(searchData, getActivity());

						onSearchSuccessFinish();
					}

					@Override
					public void onProgressUpdate(int i) {
						mCircleProgressView.setProgress(i/10);
					}

					@Override
					public void onCanceled() {

					}

					@Override
					public void onError(int errorCode, int responseCode, String searchId) {
						if (getActivity() == null) {
							return;
						}

						switch (errorCode) {
							case ApiExceptions.NO_RESULTS_EXCEPTION:
								showToastAndReturnToSearchForm(getString(R.string.alert_no_results));
								break;
							case ApiExceptions.SERVER_EXCEPTION:
							case ApiExceptions.API_EXCEPTION:
								showToastAndReturnToSearchForm(getString(R.string.toast_error_api));
								break;
							case ApiExceptions.CONNECTION_EXCEPTION:
								if (isDetached()) return;
								showToastAndReturnToSearchForm(getString(R.string.toast_error_connection));
								break;
							case ApiExceptions.WRONG_SIGNATURE_EXCEPTION:
								showToastAndReturnToSearchForm(getString(R.string.signature_toast));
								break;
							case ApiExceptions.IO_EXCEPTION:
							default:
								showToastAndReturnToSearchForm(getString(R.string.toast_error_unknown));
						}
					}
				});
				break;
		}
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	protected void resumeDialog(String removedDialogFragmentTag) {
	}

	private void onSearchSuccessFinish() {
		if (getActivity() == null) {
			return;
		}

		if (mCircleProgressView.getProgress() == 100) {
			showResults();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ValueAnimator progressAnimator = ValueAnimator.ofFloat(mCircleProgressView.getProgress(), PROGRESS_BAR_LENGTH);
			progressAnimator.setDuration(ANIMATION_FINISH_DURATION);
			progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
			progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					Float progress = (Float) animation.getAnimatedValue();
					mCircleProgressView.setProgress(progress/10);
				}
			});
			progressAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					showResults();
				}
			});
			progressAnimator.start();
		} else {
			showResults();
		}

	}

	private void showToastAndReturnToSearchForm(String toast) {
		if (getActivity() == null) return;
		Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
		if (!isPaused) {
			getActivity().onBackPressed();
		}
	}

	private void showResults() {
		if (!isPaused) {
			popFragmentFromBackStack();
			startFragment(ResultsFragment.newInstance(), true);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		isPaused = false;
		switch (AviasalesSDK.getInstance().getSearchingTicketsStatus()) {
			case CANCELED:
			case ERROR:
				getActivity().onBackPressed();
				break;
			case FINISHED:
				showResults();
				break;
		}
	}

	@Override
	public void onPause() {
		isPaused = true;
		super.onPause();
	}
}
