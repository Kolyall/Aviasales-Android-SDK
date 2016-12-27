package com.avia.application.filters;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BaseNumericFilter implements Serializable {

    @Getter @Setter protected int maxValue = Integer.MIN_VALUE;
    @Getter @Setter protected int minValue = Integer.MAX_VALUE;
    @Getter @Setter protected int currentMaxValue;
    @Getter @Setter protected int currentMinValue;

    public BaseNumericFilter() {

    }

    public BaseNumericFilter(BaseNumericFilter numericFilter) {
        maxValue = numericFilter.getMaxValue();
        minValue = numericFilter.getMinValue();
        currentMaxValue = numericFilter.getCurrentMaxValue();
        currentMinValue = numericFilter.getCurrentMinValue();
    }

    public void mergeFilter(BaseNumericFilter filter) {
        if (filter.isActive()) {
            currentMinValue = Math.min(Math.max(filter.getCurrentMinValue(), minValue), maxValue);
            currentMaxValue = Math.max(Math.min(filter.getCurrentMaxValue(), maxValue), minValue);
        }
    }

    public boolean isActive() {
        return !(maxValue == currentMaxValue && minValue == currentMinValue);
    }

    public boolean isValid() {
        return !(maxValue == Integer.MIN_VALUE || minValue == Integer.MAX_VALUE);
    }

    public boolean isEnabled() {
        return maxValue != minValue;
    }

    public void clearFilter() {
        currentMaxValue = maxValue;
        currentMinValue = minValue;
    }

    protected boolean isActual(long value) {
        return value >= currentMinValue && value <= currentMaxValue;
    }

    protected boolean isActualForMaxValue(int value) {
        return value <= currentMaxValue;
    }

    protected boolean isActualForMinValue(int value) {
        return value >= currentMinValue;
    }
}
