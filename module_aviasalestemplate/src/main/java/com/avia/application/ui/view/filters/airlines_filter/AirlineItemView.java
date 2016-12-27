package com.avia.application.ui.view.filters.airlines_filter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.avia.application.R;
import com.avia.application.filters.FilterCheckedAirline;

import ru.aviasales.expandedlistview.view.BaseFiltersListViewItem;


public class AirlineItemView extends BaseFiltersListViewItem {

	private final RatingBar ratingBar;

	public AirlineItemView(Context context) {
		this(context, null);
	}

	public AirlineItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		airlineViewStub.inflate();
		ratingBar = (RatingBar) findViewById(R.id.rbar_base_filter_list_item);
	}

	public void setCheckedAirport(FilterCheckedAirline checkedAirline) {
		super.setCheckedText(checkedAirline);
	}

	public void setRatingBar(float rating) {
		ratingBar.setRating(rating);
	}

	public RatingBar getRatingBar() {
		return ratingBar;
	}

}