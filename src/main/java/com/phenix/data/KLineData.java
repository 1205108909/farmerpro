package com.phenix.data;

import com.phenix.farmer.feature.Featurable;
import com.phenix.orderbook.TimeSeriesSecurityData;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by yangfei on 2017/4/27.
 */
public class KLineData extends TimeSeriesSecurityData {
    @Getter
    private Security security;
    @Getter
    private LocalDate date;
    @Getter
    private LocalTime start;
    @Getter
    private LocalTime end;
    @Getter
    private double open;
    @Getter
    private double high;
    @Getter
    private double low;
    @Getter
    private double cls;
    @Getter
    private double avg;
    @Getter
    private double vol;
    @Getter
    private double amount;
    @Getter
    private int intervalInSecs;
    @Getter
    private Class<? extends Featurable> type;

    public KLineData(Security sec_, LocalDate date_, LocalTime start_, LocalTime end_, double open_, double high_, double low_, double cls_, double avg_,
                     double vol_, double amount_, int intervalInSecs_, Class<? extends Featurable> type_) {
        this.security = sec_;
        this.date = date_;
        this.start = start_;
        this.end = end_;
        this.open = open_;
        this.high = high_;
        this.low = low_;
        this.cls = cls_;
        this.avg = avg_;
        this.vol = vol_;
        this.amount = amount_;
        this.intervalInSecs = intervalInSecs_;
        this.type = type_;
    }

    @Override
    public LocalTime getTime() {
        return end;
    }

    public SecurityType getSecurityType() {
        return security.getType();
    }

    public String getSymbol() {
        return security.getSymbol();
    }

    public int getTimeInSecs() {
        return end.toSecondOfDay();
    }
}
