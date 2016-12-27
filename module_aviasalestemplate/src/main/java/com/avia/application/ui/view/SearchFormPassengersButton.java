package com.avia.application.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avia.application.R;

import ru.aviasales.core.search.params.Passengers;


public class SearchFormPassengersButton extends RelativeLayout {

	private static final int VIEW_LAYOUT = R.layout.search_form_passengers_btn;
	private TextView tvAdults;
	private TextView tvChildren;
	private TextView tvInfants;
	private ImageView ivAdult;
	private ImageView ivChild;
	private ImageView ivInfant;

	public SearchFormPassengersButton(Context context) {
		super(context);
	}

	public SearchFormPassengersButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setUpViews(context);
	}

	public SearchFormPassengersButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setUpViews(context);
	}

	private void setUpViews(Context context) {
		LayoutInflater.from(context).inflate(VIEW_LAYOUT, this, true);
		ivAdult = (ImageView) findViewById(R.id.iv_adult);
		ivChild = (ImageView) findViewById(R.id.iv_child);
		ivInfant = (ImageView) findViewById(R.id.iv_infant);

		tvAdults = (TextView) findViewById(R.id.tv_adults);
		tvChildren = (TextView) findViewById(R.id.tv_children);
		tvInfants = (TextView) findViewById(R.id.tv_infants);
		setClickable(true);
	}

	public void setData(Passengers passengers) {

		int adults = passengers.getAdults();
		int children = passengers.getChildren();
		int infants = passengers.getInfants();

		tvAdults.setText(Integer.toString(adults));
		tvChildren.setText(Integer.toString(children));
		tvInfants.setText(Integer.toString(infants));

		tvAdults.setEnabled(adults > 0);
		tvChildren.setEnabled(children > 0);
		tvInfants.setEnabled(infants > 0);

		updateColors(passengers);
	}

	private void updateColors(Passengers passengers) {
		int adults = passengers.getAdults();
		int children = passengers.getChildren();
		int infants = passengers.getInfants();

		updateTintImageColor(ivAdult,adults);
		updateTintImageColor(ivChild,children);
		updateTintImageColor(ivInfant,infants);
	}

	private void updateTintImageColor(ImageView imageView, int number) {
		if (number == 0){
			imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_blue));
		}else{
			imageView.setColorFilter(ContextCompat.getColor(getContext(),R.color.blue_main));
		}
	}
}
