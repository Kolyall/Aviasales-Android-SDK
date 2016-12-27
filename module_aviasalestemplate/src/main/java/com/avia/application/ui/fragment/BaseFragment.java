package com.avia.application.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.avia.application.utils.BackPressable;
import com.avia.application.ui.dialog.BaseDialogFragment;

public abstract class BaseFragment extends Fragment implements BackPressable {

	public static final String DIALOG_TAG = "aviasales_dialog";
	public static final String PREFERENCES_NAME = "ru.aviasales";
	public static final String EXTRA_REMOVED_DIALOG = "removed_dialog";

	private BaseDialogFragment dialogFragment;
	protected String removedDialogFragmentTag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			removedDialogFragmentTag = savedInstanceState.getString(EXTRA_REMOVED_DIALOG);
		}
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {

		if (removedDialogFragmentTag != null) {
			resumeDialog(removedDialogFragmentTag);
			removedDialogFragmentTag = null;
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		dismissDialogWithSave();
		super.onPause();
	}

	private void showDialog() {
		dialogFragment.show(getParentFragmentManager(), DIALOG_TAG);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		workaroundForPreHoneycomb();

		if (removedDialogFragmentTag != null) {
			outState.putString(EXTRA_REMOVED_DIALOG, removedDialogFragmentTag);
		}
		super.onSaveInstanceState(outState);
	}

	private void workaroundForPreHoneycomb() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			dismissDialogWithSave();
		}
	}

	protected void createDialog(final BaseDialogFragment dialogFragment) {
		if (dialogFragment == null || getActivity() == null)
			return;

		removeDialogFragment();

		this.dialogFragment = dialogFragment;
		showDialog();
	}


	protected void setDisplayOptions(int displayShowTitle) {
		if (getActionBar() == null) {
			return;
		}
		getActionBar().setDisplayOptions(displayShowTitle);
	}

	public void showBackButtonOnToolbar() {
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setHomeActionContentDescription("Back");
		}
	}
	public void hideBackButtonOnToolbar() {
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
		}
	}

	protected void showActionBar(boolean isShowActionBar) {
		if (getActionBar() == null) {
			return;
		}
		getActionBar().setShowHideAnimationEnabled(false);
		if (isShowActionBar) {
			getActionBar().show();
		} else {
			getActionBar().hide();
		}
	}

	@Nullable
	protected ActionBar getActionBar() {
		return ((AppCompatActivity) getActivity()).getSupportActionBar();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				getActivity().onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setTextToActionBar(String textToActionBar) {
		if (getActionBar() == null) {
			return;
		}
		getActionBar().setTitle(textToActionBar);
	}

	private void removeDialogFragment() {
		if (getActivity() != null) {
			FragmentTransaction ft = getParentFragmentManager().beginTransaction();
			Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
			if (prev != null) {
				ft.remove(prev);
			}
			ft.commitAllowingStateLoss();
		}
	}

	private void dismissDialogWithSave() {
		if (dialogFragment != null) {
			removedDialogFragmentTag = dialogFragment.getFragmentTag();
		}
		dismissDialog();
	}

	protected void dismissDialog() {
		if (dialogFragment != null && dialogFragment.isVisible()) {
			dialogFragment.dismissAllowingStateLoss();
		}
		removeDialogFragment();
		dialogFragment = null;
	}

	protected SharedPreferences getPreferences() {
		return getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

	protected boolean dialogIsVisible() {
		return dialogFragment != null && dialogFragment.isVisible();
	}

	protected void startFragment(BaseFragment fragment, boolean shouldAddToBackStack) {

		Fragment parentFragment = getParentFragment();

		if (parentFragment instanceof AviasalesFragment) {
			((AviasalesFragment) parentFragment).startFragment(fragment, shouldAddToBackStack);
		}

	}

	protected void popBackStackInclusive(String tag) {
		if (getActivity() != null) {
			Fragment parentFragment = getParentFragment();

			if (parentFragment instanceof AviasalesFragment) {
				((AviasalesFragment) parentFragment).popBackStackInclusive(tag);
			}
		}
	}

	protected void popFragmentFromBackStack() {
		if (getActivity() != null) {
			Fragment parentFragment = getParentFragment();

			if (parentFragment instanceof AviasalesFragment) {
				((AviasalesFragment) parentFragment).popFragmentFromBackStack();
			}
		}
	}


	private FragmentManager getParentFragmentManager() {

		Fragment parentFragment = getParentFragment();

		if (parentFragment instanceof AviasalesFragment) {
			return ((AviasalesFragment) parentFragment).getAviasalesFragmentManager();
		} else {
			return getFragmentManager();
		}
	}

	public boolean isFinishing(Activity activity){
		return activity==null||isRemoving()||!isVisible()||activity.isFinishing();
	}

	protected abstract void resumeDialog(String removedDialogFragmentTag);

	@Override
	public boolean onBackPressed() {
		return false;
	}

}
