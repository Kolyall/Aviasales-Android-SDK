package com.avia.application.ui.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avia.application.R;
import com.avia.application.ui.view.PassengersPickerView;

import ru.aviasales.core.search.params.Passengers;


public class PassengersDialogFragment extends BaseDialogFragment {

	public static final String TAG = "PassengersDialogFragment";

	public static final int ADULTS_ID = 100;
	public static final int CHILDREN_ID = 101;
	public static final int INFANTS_ID = 102;
	private static final int VIEW_LAYOUT = R.layout.passangers_picker_fragment;

	private int adults;
	private int children;
	private int infants;

	private PassengersPickerView npAdults;
	private PassengersPickerView npChildren;
	private PassengersPickerView npInfants;

	OnPassengersChangedListener onPassengersChangedListener;

	public interface OnPassengersChangedListener {
		void onPassengersChanged(Passengers passengers);
		void onCancel();
	}

	public PassengersDialogFragment() {
	}

	@SuppressLint("ValidFragment")
	public PassengersDialogFragment(Passengers passengers, OnPassengersChangedListener onPassengersChangedListener) {

		int adults = passengers.getAdults();
		int children = passengers.getChildren();
		int infants = passengers.getInfants();

		if (checkValues(adults, children, infants)) {
			this.adults = adults;
			this.children = children;
			this.infants = infants;
		} else {
			this.adults = 1;
			this.children = 0;
			this.infants = 0;
		}
		this.onPassengersChangedListener = onPassengersChangedListener;

		setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
	}


	private PassengersPickerView.OnChangeListener onChangeListener = new PassengersPickerView.OnChangeListener() {
		@Override
		public void onChange(int newValue, int id) {
			switch (id) {
				case ADULTS_ID: {
					adults = newValue;
					break;
				}
				case CHILDREN_ID: {
					children = newValue;
					break;
				}
				case INFANTS_ID: {
					infants = newValue;
					break;
				}
			}
			setAvailableChanges();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//	setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(VIEW_LAYOUT, container, false);

		npAdults = (PassengersPickerView) layout.findViewById(R.id.pp_adults);
		npChildren = (PassengersPickerView) layout.findViewById(R.id.pp_children);
		npInfants = (PassengersPickerView) layout.findViewById(R.id.pp_infants);

		npAdults.setDefaultNumber(adults);
		npChildren.setDefaultNumber(children);
		npInfants.setDefaultNumber(infants);

		npAdults.setId(ADULTS_ID);
		npChildren.setId(CHILDREN_ID);
		npInfants.setId(INFANTS_ID);

		npAdults.setOnChangeListener(onChangeListener);
		npChildren.setOnChangeListener(onChangeListener);
		npInfants.setOnChangeListener(onChangeListener);

		layout.findViewById(R.id.tv_positive_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onPassengersChangedListener.onPassengersChanged(new Passengers(adults, children, infants));
				dismissDialog();
			}
		});

		setAvailableChanges();

		return layout;
	}

	private boolean checkValues(int adults, int children, int infants) {
		if (adults < 1 || children < 0 || infants < 0 || adults > 9 || children > 8) {
			return false;
		}
		if (adults + children > 9) {
			return false;
		}
		if (adults < infants) {
			return false;
		}
		return true;
	}


	private void setAvailableChanges() {
		setLimits(npAdults, checkAdultsWithInfants(adults), checkAdults(adults + 1));
		setLimits(npChildren, checkChildren(children - 1), checkChildren(children + 1));
		setLimits(npInfants, checkInfants(infants - 1), checkInfants(infants + 1));
	}

	private boolean checkAdultsWithInfants(int adults) {
		boolean notDisabled = true;
		if (adults < infants) {
			npInfants.decreaseNumber();
			infants--;
			notDisabled = true;
		}
		if (adults <= 1) {
			notDisabled = false;
		}
		return notDisabled;
	}

	private boolean checkAdults(int adults) {
		return checkValues(adults, children, infants);
	}

	private boolean checkChildren(int children) {
		return checkValues(adults, children, infants);
	}

	private boolean checkInfants(int infants) {
		return checkValues(adults, children, infants);
	}

	private void setLimits(PassengersPickerView np, boolean isDecreaseAvailable, boolean isIncreaseAvailable) {
		np.setDecreaseEnabled(isDecreaseAvailable);
		np.setIncreaseEnabled(isIncreaseAvailable);
	}

	@Override
	public void onStart() {
		super.onStart();

		if (getDialog() == null) {
			return;
		}

		int dialogWidth = getResources().getDimensionPixelSize(R.dimen.passengers_dialog_width);
		int dialogHeight = getResources().getDimensionPixelSize(R.dimen.passengers_dialog_height);

		getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		onPassengersChangedListener.onCancel();
		super.onCancel(dialog);
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}
}
