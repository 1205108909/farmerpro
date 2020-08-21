package com.phenix.farmer.feature;

import com.phenix.data.Security;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.lang.Double.NaN;

/**
 * @author phenix
 * @time 22:34
 */
public final class GreenKLineVolStat extends DailyFeatureStat implements Featurable {
    public final static GreenKLineVolStat INVALID = new GreenKLineVolStat(Security.UNKNOWN, LocalDate.MIN, LocalTime.MIN,
            NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN);

    public GreenKLineVolStat(Security s_, LocalDate d_, LocalTime t_, double q5_, double q10_, double q20_, double q30_, double q40_,
                             double q50_, double q60_, double q70_, double q80_, double q90_, double q95_, double min_,
                             double max_, double mean_, double std_) {
        super(s_, d_, t_, q5_, q10_, q20_, q30_, q40_, q50_, q60_, q70_, q80_, q90_, q95_, min_, max_, mean_, std_);
    }

    @Override
    public boolean isValid() {
        return this != INVALID;
    }

    @Override
    public boolean equals(Object obj_) {
        return super.equals(obj_);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " | " + super.toString();
    }
}
