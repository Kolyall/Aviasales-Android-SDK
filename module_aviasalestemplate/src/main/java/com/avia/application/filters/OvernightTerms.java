package com.avia.application.filters;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class OvernightTerms implements Serializable {
    @Getter @Setter private int minDurationMinutes;
    @Getter @Setter private int maxDurationMinutes;

    @Getter @Setter private int minLandingTime;
    @Getter @Setter private int maxLandingTime;

    public OvernightTerms() {
    }

    public OvernightTerms(int minDurationHours, int maxDurationHours, int minLandingTime, int maxLandingTime) {
        this.minLandingTime = minLandingTime;
        this.maxLandingTime = maxLandingTime;
        this.minDurationMinutes = minDurationHours * 60;
        this.maxDurationMinutes = maxDurationHours * 60;
    }

    public OvernightTerms(OvernightTerms overnightTerms) {
        minLandingTime = overnightTerms.getMinLandingTime();
        maxLandingTime = overnightTerms.getMaxLandingTime();
        maxDurationMinutes = overnightTerms.getMaxDurationMinutes();
        minDurationMinutes = overnightTerms.getMinDurationMinutes();
    }

    public boolean isOvernight(long durationMinutes, long landingTimeHours) {
        if (isInInterval(durationMinutes)) {
            if (minLandingTime < maxLandingTime) {
                if (landingTimeHours >= minLandingTime && landingTimeHours < maxLandingTime) {
                    return true;
                }
            } else {
                if (landingTimeHours >= minLandingTime || landingTimeHours < maxLandingTime) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInInterval(long duration) {
        return duration >= minDurationMinutes && duration < maxDurationMinutes;
    }
}