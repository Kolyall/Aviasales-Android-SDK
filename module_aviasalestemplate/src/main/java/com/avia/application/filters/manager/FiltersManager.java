package com.avia.application.filters.manager;

import android.content.Context;
import android.os.Handler;

import com.avia.application.filters.FiltersSet;
import com.avia.application.filters.OpenJawFiltersSet;
import com.avia.application.filters.SimpleSearchFilters;
import com.avia.application.proposal.ProposalManager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;
import ru.aviasales.core.search.object.Proposal;
import ru.aviasales.core.search.object.SearchData;

public class FiltersManager {
	private static volatile FiltersManager instance = new FiltersManager();

	@Getter @Setter private FiltersSet filtersSet;

	private ExecutorService pool;

	private Handler mHandler = new Handler();
	private OnFilterResultListener mOnFilterResultsListener;

	@Getter @Setter private List<Proposal> filteredProposals;

	public static FiltersManager getInstance() {
		return instance;
	}

	public interface OnFilterResultListener {
		void onFilteringFinished(List<Proposal> filteredTicketsData);
	}

	public void filterSearchData(final FiltersSet localFiltersSet, final SearchData searchData, OnFilterResultListener listener) {

		mOnFilterResultsListener = listener;

		createPool();

		pool.submit(new Runnable() {
			@Override
			public void run() {
				List<Proposal> filteredTickets = localFiltersSet.applyFilters(searchData);
				mHandler.post(new EndRunnable(filteredTickets));

			}
		});

	}

	private void createPool() {
		if (pool == null) {
			pool = Executors.newCachedThreadPool();
		}
	}

	public void setOnFilterResultsListener(OnFilterResultListener onFilterResultsListener) {
		this.mOnFilterResultsListener = onFilterResultsListener;
	}

	public class EndRunnable implements Runnable {

		private List<Proposal> filteredTickets;

		public EndRunnable(List<Proposal> filteredTickets) {
			this.filteredTickets = filteredTickets;
		}

		@Override
		public void run() {
			if (mOnFilterResultsListener != null) {
				mOnFilterResultsListener.onFilteringFinished(filteredTickets);
			}
		}
	}

	public void initFilter(final SearchData searchData, final Context context) {

		createPool();

		filteredProposals = searchData.getProposals();

		pool.submit(new Runnable() {
			@Override
			public void run() {
				if (context == null) {
					return;
				}
				if (searchData.getProposals() != null) {

					if (searchData.isComplexSearch()) {
						filtersSet = new OpenJawFiltersSet(context);
					} else {
						filtersSet = new SimpleSearchFilters(context);
					}

					filtersSet.initMinAndMaxValues(context, searchData, searchData.getProposals());
					filtersSet.clearFilters();

					List<Proposal> filteredTickets = filtersSet.applyFilters(searchData);
					Collections.sort(filteredTickets, ProposalManager.getInstance().getProposalComparator());

					filteredProposals = filteredTickets;

				}

			}
		});

	}

}
