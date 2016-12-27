package com.avia.application.utils;

import com.avia.application.proposal.sort.ProposalArrivalOnReturnComparator;

import java.util.Collections;
import java.util.List;

import ru.aviasales.core.search.object.Proposal;
import com.avia.application.proposal.sort.ProposalArrivalComparator;
import com.avia.application.proposal.sort.ProposalDepartureComparator;
import com.avia.application.proposal.sort.ProposalDepartureOnReturnComparator;
import com.avia.application.proposal.sort.ProposalDurationComparator;
import com.avia.application.proposal.sort.ProposalPriceComparator;
import com.avia.application.proposal.sort.ProposalRatingComparator;

public class SortUtils {

	public static final int SORTING_BY_PRICE = 0;
	public static final int SORTING_BY_DEPARTURE = 1;
	public static final int SORTING_BY_ARRIVAL = 2;
	public static final int SORTING_BY_DEPARTURE_ON_RETURN = 3;
	public static final int SORTING_BY_ARRIVAL_ON_RETURN = 4;
	public static final int SORTING_BY_DURATION = 5;
	public static final int SORTING_BY_RATING = 6;

	private static int savedSortingType = SORTING_BY_PRICE;

	public static void sortProposals(List<Proposal> proposals, int sortingType, boolean isComplexSearch) {

		savedSortingType = sortingType;

		switch (sortingType) {
			case SORTING_BY_PRICE:
				sortByPrice(proposals);
				break;
			case SORTING_BY_DEPARTURE:
				sortByDeparture(proposals);
				break;
			case SORTING_BY_ARRIVAL:
				sortingByArrival(proposals, isComplexSearch);
				break;
			case SORTING_BY_DEPARTURE_ON_RETURN:
				soringByDepartureOnReturn(proposals);
				break;
			case SORTING_BY_ARRIVAL_ON_RETURN:
				sortingByArrivalOnReturn(proposals);
				break;
			case SORTING_BY_DURATION:
				sortingByDuration(proposals);
				break;
			case SORTING_BY_RATING:
				sortingByRating(proposals);
				break;
		}
	}

	private static void sortingByRating(final List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalRatingComparator(proposals.get(0)));
	}

	private static void sortingByDuration(List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalDurationComparator());
	}

	private static void sortingByArrivalOnReturn(List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalArrivalOnReturnComparator());
	}

	private static void soringByDepartureOnReturn(List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalDepartureOnReturnComparator());
	}

	private static void sortingByArrival(List<Proposal> proposals, final boolean isComplexSearch) {
		Collections.sort(proposals, new ProposalArrivalComparator(isComplexSearch));
	}

	private static void sortByDeparture(List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalDepartureComparator());
	}

	private static void sortByPrice(List<Proposal> proposals) {
		Collections.sort(proposals, new ProposalPriceComparator());
	}

	public static int getSavedSortingType() {
		return savedSortingType;
	}

	public static void resetSavedSortingType() {
		savedSortingType = SORTING_BY_PRICE;
	}

}
