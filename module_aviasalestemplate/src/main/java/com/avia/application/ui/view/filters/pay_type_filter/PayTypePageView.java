package com.avia.application.ui.view.filters.pay_type_filter;

import android.content.Context;
import android.util.AttributeSet;

import com.avia.application.ui.adapter.PayTypeAdapter;
import com.avia.application.ui.view.filters.BaseFiltersScrollView;

import ru.aviasales.expandedlistview.view.ExpandedListView;
import com.avia.application.filters.PayTypeFilter;

public class PayTypePageView extends BaseFiltersScrollView {
	private ExpandedListView expandedListView;
	private boolean hideTitle = true;

	public PayTypePageView(Context context) {
		super(context);
	}

	public PayTypePageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PayTypePageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setHideTitle(boolean hideTitle) {
		this.hideTitle = hideTitle;
	}

	@Override
	protected void setUpSimplePageView() {
		if (getSimpleGeneralFilters().getPayTypeFilter().isValid()) {
			expandedListView = createPayTypeListView(getSimpleGeneralFilters().getPayTypeFilter(), hideTitle);
			addView(expandedListView);
		}
	}

	@Override
	protected void setUpOpenJawPageView() {
		if (getOpenJawGeneralFilters().getPayTypeFilter().isValid()) {
			expandedListView = createPayTypeListView(getOpenJawGeneralFilters().getPayTypeFilter(), hideTitle);
			addView(expandedListView);
		}
	}

	@Override
	public void clearFilters() {
		if (expandedListView != null) {
			expandedListView.notifyDataChanged();
		}
	}

	private ExpandedListView createPayTypeListView(PayTypeFilter payTypeFilter, boolean hideTitle) {
		ExpandedListView payTypeListView = new ExpandedListView(getContext(), null);
		PayTypeAdapter adapter = new PayTypeAdapter(getContext(), payTypeFilter.getPayTypeList(), hideTitle);
		payTypeListView.setAdapter(adapter);
		payTypeListView.setOnItemClickListener(new ExpandedListView.OnItemClickListener() {
			@Override
			public void onItemClick() {
				listener.onChange();
			}
		});
		return payTypeListView;
	}
}