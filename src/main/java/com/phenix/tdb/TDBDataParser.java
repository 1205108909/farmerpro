package com.phenix.tdb;

import cn.com.wind.td.tdb.Tick;
import com.phenix.data.Security;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TDBDataParser {
    private final static double Precision = 10000;

    public static OrderBook parseTDBIndexFutureOrderBook(Tick md_) {
        String symbol = md_.getWindCode().toLowerCase();
        Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
        if(sec == null) {
            return null;
        }
        double preClose = md_.getPreClose() / Precision;
        double open = md_.getOpen() / Precision;
        double high = md_.getHigh() / Precision;
        double low = md_.getLow() / Precision;
        double last = md_.getPrice() / Precision;
        double volume = md_.getVolume();
        double turnover = md_.getTurover();
        String time = String.valueOf(md_.getTime());
        if (time.length() == 8)
            time = "0" + time;
        LocalTime lt = DateUtil.getTime2(time);
        LocalDate ld = FarmerDataManager.TODAY;

        List<Quote> ask = new ArrayList<Quote>(1);
        List<Quote> bid = new ArrayList<>(1);
        ask.add(new Quote(md_.getAskPrice()[0] / Precision, md_.getAskVolume()[0]));
        bid.add(new Quote(md_.getBidPrice()[0] / Precision, md_.getBidVolume()[0]));
        OrderBook ob = OrderBook.of(sec, ask, bid, LocalDateTime.of(ld, lt));
        ob.setPreClose(preClose);
        ob.setOpenPx(open);
        ob.setHighPx(high);
        ob.setLowPx(low);
        ob.setLastPx(last);
        ob.setVolume(volume);
        ob.setTurnover(turnover);

        return ob;
    }

    public static OrderBook parseTDBIndexOrderBook(Tick md_) {
        String symbol = md_.getWindCode().toLowerCase();
        //System.out.println(symbol);
        Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
        if(sec == null) {
            return null;
        }
        double preClose = md_.getPreClose() / Precision;
        double open = md_.getOpen() / Precision;
        double high = md_.getHigh() / Precision;
        double low = md_.getLow() / Precision;
        double last = md_.getPrice() / Precision;
        double volume = md_.getVolume();
        double turnover = md_.getTurover() * 100;
        String time = String.valueOf(md_.getTime());
        if (time.length() == 8)
            time = "0" + time;
        LocalTime lt = DateUtil.getTime2(time);
        LocalDate ld = FarmerDataManager.TODAY;

        OrderBook ob = OrderBook.of(sec, Collections.emptyList(), Collections.emptyList(), LocalDateTime.of(ld, lt));
        ob.setPreClose(preClose);
        ob.setOpenPx(open);
        ob.setHighPx(high);
        ob.setLowPx(low);
        ob.setLastPx(last);
        ob.setVolume(volume);
        ob.setTurnover(turnover);
        return ob;
    }
}
