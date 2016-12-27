package com.avia.application.ui.listener;

import ru.aviasales.core.search_airports.object.PlaceData;

public interface OnPlaceSelectedListener {
	void onAirportSelected(PlaceData placeData, int typeOfDate, int segment, boolean isComplex);
}
