package com.phenix.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by phenix on 2017/2/10.
 */
public class DailyStat {
    @Setter
    @Getter
    private double adv10;

    @Setter
    @Getter
    private LocalDate date;

    @Setter
    @Getter
    private double closePx;

    @Setter
    @Getter
    private double openPx;

    @Setter
    @Getter
    private double highPx;
    @Getter
    @Setter
    private double highValue;//high/preCls - 1

    @Setter
    @Getter
    private LocalTime highTime;

    @Setter
    @Getter
    private double lowPx;
    @Getter
    @Setter
    private double lowValue;//low/preCls - 1

    @Getter
    @Setter
    private LocalTime lowTime;

    @Setter
    @Getter
    private double vol;

    @Setter
    @Getter
    private double amount;

    @Setter
    @Getter
    private long ashares;

    @Setter
    @Getter
    private long afloats;

    @Setter
    @Getter
    private double adjustingFactor;

    @Setter
    @Getter
    private double AdjustingConst;

    @Setter
    @Getter
    private LocalDate previousTradingDate;

    @Setter
    @Getter
    private double preClose;

    public DailyStat(double adv10_, LocalDate date_) {
        this.adv10 = adv10_;
        this.date = date_;
        this.lowPx = Double.MAX_VALUE;
        this.highPx = Double.MIN_VALUE;
    }

    public int getHighTimeInSecs() {
        return highTime.toSecondOfDay();
    }

    public int getLowTimeInSecs() {
        return lowTime.toSecondOfDay();
    }
}