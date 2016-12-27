package com.avia.application.ui.view.filters.stop_over_and_price_filters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.avia.application.R;
import com.avia.application.filters.BaseNumericFilter;
import com.avia.application.filters.OpenJawFiltersSet;
import com.avia.application.filters.OvernightFilter;
import com.avia.application.filters.SegmentFilter;
import com.avia.application.filters.SimpleSearchFilters;
import com.avia.application.ui.listener.OnRangeChangeListener;
import com.avia.application.ui.view.filters.BaseFilterView;
import com.avia.application.ui.view.filters.BaseFiltersScrollView;
import com.avia.application.ui.view.filters.SegmentExpandableView;

import java.util.ArrayList;
import java.util.List;


public class StopOverAndPriceFiltersPageView extends BaseFiltersScrollView {
	private BaseFilterView priceFilterView;
	private final List<StopOverFilterView> stopOverFilterViews = new ArrayList<>();

	public StopOverAndPriceFiltersPageView(Context context) {
		super(context);
	}

	public StopOverAndPriceFiltersPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StopOverAndPriceFiltersPageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void setUpSimplePageView() {
		SimpleSearchFilters filters = getSimpleGeneralFilters();
		if (filters.getStopOverSizeFilter().isValid() || filters.getStopOverDelayFilter().isValid()) {
			StopOverFilterView view = createStopOverFilterView(filters.getStopOverSizeFilter(),
					filters.getStopOverDelayFilter(), filters.getOvernightFilter());
			stopOverFilterViews.add(view);
			addView(view);
		}

		if (filters.getPriceFilter().isValid()) {
			priceFilterView = createPriceFilterView(getSimpleGeneralFilters().getPriceFilter());

			addView(LayoutInflater.from(getContext()).inflate(R.layout.filters_divider, layout, false));
			addView(priceFilterView);
		}
	}

	@Override
	protected void setUpOpenJawPageView() {
		OpenJawFiltersSet filters = getOpenJawGeneralFilters();

		for (Integer segmentNumber : filters.getSegmentFilters().keySet()) {
			SegmentFilter segmentFilter = filters.getSegmentFilters().get(segmentNumber);
			if (!segmentFilter.getStopOverCountFilter().isValid() && !segmentFilter.getStopOverDelayFilter().isValid()) {
				continue;
			}

			SegmentExpandableView segmentExpandableView = createSegmentExpandableView(segmentList.get(segmentNumber));
			StopOverFilterView view = createStopOverFilterView(segmentFilter.getStopOverCountFilter(),
					segmentFilter.getStopOverDelayFilter(),
					segmentFilter.getOvernightFilter());
			stopOverFilterViews.add(view);
			segmentExpandableView.addContentView(view);
			segmentExpandableView.addContentView(LayoutInflater.from(getContext()).inflate(R.layout.filters_divider,
					segmentExpandableView.getContentLayout(), false));
			addView(segmentExpandableView);
		}

		priceFilterView = createPriceFilterView(filters.getPriceFilter());
		addView(priceFilterView);
	}

	@Override
	public void clearFilters() {
		priceFilterView.clear();
		for (StopOverFilterView view : stopOverFilterViews) {
			view.clearFilterViews();
		}
	}

	private BaseFilterView createPriceFilterView(final BaseNumericFilter priceFilter) {
		BaseFilterView filterView = new BaseFilterView(getContext(), null, BaseFilterView.PRICE_FILTER,
				priceFilter.getMinValue(), priceFilter.getMaxValue(),
				priceFilter.getCurrentMaxValue(),
				new OnRangeChangeListener() {
					@Override
					public void onChange(int max) {
						priceFilter.setCurrentMaxValue(max);
						listener.onChange();
					}
				});
		filterView.setEnabled(priceFilter.isEnabled());
		return filterView;
	}

	private StopOverFilterView createStopOverFilterView(final BaseNumericFilter stopOverCountFilter,
	                                                    final BaseNumericFilter stopOverDurationFilter,
	                                                    final OvernightFilter overnightFilter) {
		StopOverFilterView stopOverFilterView = new StopOverFilterView(getContext());
		stopOverFilterView.init(stopOverCountFilter, stopOverDurationFilter,
				overnightFilter, new StopOverFilterView.OnStopOverFilterChangedListener() {

					@Override
					public void onStopOverCountChanged(int max) {
						stopOverCountFilter.setCurrentMaxValue(max);
						listener.onChange();
					}

					@Override
					public void onStopOverDurationChanged(int min, int max) {
						stopOverDurationFilter.setCurrentMinValue(min);
						stopOverDurationFilter.setCurrentMaxValue(max);
						listener.onChange();
					}

					@Override
					public void onOvernightChanged(boolean overnight) {
						overnightFilter.setAirportOvernightAvailable(overnight);
						listener.onChange();
					}
				});
		return stopOverFilterView;
	}
}