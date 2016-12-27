package com.avia.application.ui.listener;

import com.avia.application.ui.model.SearchFormData;

public interface AviasalesImpl extends OnPlaceSelectedListener {

	SearchFormData getSearchFormData();

	void saveState();

}
