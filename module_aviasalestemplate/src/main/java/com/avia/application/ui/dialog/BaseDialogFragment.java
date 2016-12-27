package com.avia.application.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.avia.application.ui.listener.BaseDialogInterface;


public abstract class BaseDialogFragment extends DialogFragment implements BaseDialogInterface {

	public final static String TAG = "fragment.BaseDialogFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void onCurrencyChanged() {
	}


	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
			getDialog().setDismissMessage(null);
		super.onDestroyView();
	}

	protected void dismissDialog() {
		if (getDialog() != null) {
			getDialog().dismiss();
		}
	}

}
