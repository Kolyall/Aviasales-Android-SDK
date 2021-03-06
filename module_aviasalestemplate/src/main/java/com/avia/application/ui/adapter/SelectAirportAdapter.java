package com.avia.application.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avia.application.R;
import com.avia.application.utils.Utils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.aviasales.core.search_airports.object.PlaceData;

public class SelectAirportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	@Getter @Setter private List<PlaceData> airports;
	@Getter @Setter private View.OnClickListener onClickListener;

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.select_airport_item_view, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public int getItemCount() {
		if (airports != null) {
			return airports.size();
		} else
			return 0;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
		viewHolder.itemView.setTag(airports.get(i));
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (onClickListener != null) {
					onClickListener.onClick(view);
				}
			}
		});
		((ViewHolder) viewHolder).iata.setText(Utils.capitalizeFirstLetter(airports.get(i).getIata()));
		((ViewHolder) viewHolder).cityName.setText(Utils.capitalizeFirstLetter(airports.get(i).getCityName()));
		if (airports.get(i).getAirportName() == null) {
			((ViewHolder) viewHolder).airportName.setText(viewHolder.itemView.getContext().getString(R.string.select_airport_any_airport));
		} else {
			String airportText = airports.get(i).getAirportName().toUpperCase();
			if (airports.get(i).getCityName() != null) {
				airportText += ", " + airports.get(i).getCityName().toUpperCase();
			}
			((ViewHolder) viewHolder).airportName.setText(airportText);
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView iata;
		TextView airportName;
		TextView cityName;

		public ViewHolder(View itemView) {
			super(itemView);
			iata = (TextView) itemView.findViewById(R.id.txtv_destination_fragment_search_item_iata);
			airportName = (TextView) itemView.findViewById(R.id.txtv_destination_fragment_search_item_airport);
			cityName = (TextView) itemView.findViewById(R.id.txtv_destination_fragment_search_item_city_country);
		}
	}
}
