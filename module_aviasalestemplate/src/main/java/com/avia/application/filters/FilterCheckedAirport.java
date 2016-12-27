package com.avia.application.filters;

import java.io.Serializable;
import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
import ru.aviasales.expandedlistview.view.BaseCheckedText;

public class FilterCheckedAirport extends BaseCheckedText implements Serializable {
    @Getter @Setter private String iata;
    @Getter @Setter private String city;
    @Getter @Setter private String country;
    @Getter private float rating;

    public final static Comparator<FilterCheckedAirport> cityComparator = new Comparator<FilterCheckedAirport>() {
        @Override
        public int compare(FilterCheckedAirport lhs, FilterCheckedAirport rhs) {
            return lhs.getCity().toLowerCase().compareTo(rhs.getCity().toLowerCase());
        }
    };

    public FilterCheckedAirport(String iata) {
        this.iata = iata;
    }

    public FilterCheckedAirport() {
        super();
    }

    public FilterCheckedAirport(FilterCheckedAirport checkedAirport) {
        iata = checkedAirport.getIata();
        city = checkedAirport.getCity();
        country = checkedAirport.getCountry();
        rating = checkedAirport.getRating();
        checked = checkedAirport.isChecked();
    }

    public void setRating(double rating) {
        this.rating = (float) rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilterCheckedAirport that = (FilterCheckedAirport) o;

        return Float.compare(that.rating, rating) == 0 && !(iata != null ? !iata.equals(that.iata) : that.iata != null) && !(city != null ? !city
                .equals(that.city) : that.city != null) && !(country != null ? !country.equals(that.country) : that.country != null);

    }

    @Override
    public int hashCode() {
        int result = iata != null ? iata.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        return result;
    }
}
