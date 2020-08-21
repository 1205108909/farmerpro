package com.phenix.farmer.feature;

import com.phenix.data.Security;
import com.phenix.exception.NotSupportedException;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author phenix
 * @time 0:23
 */
@Value
@NonFinal
public abstract class DailyFeatureStat implements Featurable {
    public final static Class<?>[] CLAZZ = new Class[]{Security.class, LocalDate.class, LocalTime.class,
            double.class, double.class, double.class, double.class, double.class, double.class,
            double.class, double.class, double.class, double.class, double.class, double.class,
            double.class, double.class, double.class
    };

    public final static double[] DEFAULT_QUANTILES = new double[]{5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95};

    private Security sec;
    private LocalDate date;
    private LocalTime time;
    private double q5;
    private double q10;
    private double q20;
    private double q30;
    private double q40;
    private double q50;
    private double q60;
    private double q70;
    private double q80;
    private double q90;
    private double q95;
    private double min;
    private double max;
    private double mean;
    private double std;

    public abstract boolean isValid();

    @Override
    public boolean equals(Object obj_) {
        if (obj_ == null) {
            return false;
        }
        if (obj_ == this) {
            return true;
        }
        if (obj_.getClass() != getClass()) {
            return false;
        }
        DailyFeatureStat rhs = (DailyFeatureStat) obj_;
        return sec.equals(rhs.sec) && date.equals(date);
    }

    @Override
    public int hashCode() {
        throw new NotSupportedException("FutureIndexSpreadStat is not allowed to be used as hash key");
    }

//    public final static DailyFeatureStat INVALID = new DailyFeatureStat(Security.UNKNOWN, LocalDate.MIN,
//            NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN, NaN);
}
