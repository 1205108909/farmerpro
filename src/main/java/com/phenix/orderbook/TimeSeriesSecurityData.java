package com.phenix.orderbook;

import com.phenix.data.KLineData;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;

import java.time.LocalTime;
import java.util.Comparator;

public abstract class TimeSeriesSecurityData {
    public abstract LocalTime getTime();
    public abstract Security getSecurity();

    public final static Comparator<TimeSeriesSecurityData> COMPARATOR_DATATIME_FIRST = (e1, e2) -> {
        int res = e1.getTime().compareTo(e2.getTime());
        if(res != 0 ) return res;
        if(SecurityType.INDEX_FUTURE == e1.getSecurityType() && SecurityType.INDEX_FUTURE != e2.getSecurityType())
            return -1;
        else if(SecurityType.INDEX_FUTURE != e1.getSecurityType() && SecurityType.INDEX_FUTURE == e2.getSecurityType())
            return 1;
        else {
            if(e1.getClass() != e2.getClass()) {
                int p1 = getPrecedence(e1);
                int p2 = getPrecedence(e2);
                res = Integer.compare(p1, p2);
                if(res != 0)
                    return res;
            }
            return e1.getSecurity().compareTo(e2.getSecurity());
        }
    };

    public SecurityType getSecurityType() {
        return getSecurity().getType();
    }

    public static int getPrecedence(TimeSeriesSecurityData d_) {
        if(d_ instanceof OrderBook)
            return 1;
        else if(d_ instanceof OrderFlow)
            return 2;
        else if(d_ instanceof KLineData)
            return 3;
        else if(d_ instanceof Transaction)
            return 4;
        return 100;
    }
}
