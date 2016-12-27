package com.avia.application.filters;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ru.aviasales.expandedlistview.view.BaseCheckedText;

public class FilterCheckedAirline extends BaseCheckedText implements Serializable {

    @Getter @Setter private String iata;
    @Getter private float rating;
    @Getter @Setter private int minimalPrice = Integer.MAX_VALUE;

    public FilterCheckedAirline(FilterCheckedAirline checkedAirline) {
        super(checkedAirline);
        iata = checkedAirline.getIata();
        rating = checkedAirline.getRating();
        minimalPrice = checkedAirline.getMinimalPrice();
    }

    @Override
    public void setChecked(Boolean checked) {
        super.setChecked(checked);
    }

    public FilterCheckedAirline(String iata) {
        this.iata = iata;
    }

    public String getAirline() {
        return iata;
    }

    public void setAirline(String iata) {
        this.iata = iata;
    }

    public void setRating(double rating) {
        this.rating = (float) rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilterCheckedAirline that = (FilterCheckedAirline) o;

        return Float.compare(that.rating, rating) == 0 && minimalPrice == that.minimalPrice && !(iata != null ? !iata.equals(that.iata) : that.iata != null);

    }

    @Override
    public int hashCode() {
        int result = iata != null ? iata.hashCode() : 0;
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + minimalPrice;
        return result;
    }
}
