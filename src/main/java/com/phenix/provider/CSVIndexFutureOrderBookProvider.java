package com.phenix.provider;

import com.phenix.data.Security;
import com.phenix.exception.IllegalDataException;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CSVIndexFutureOrderBookProvider extends AbstractIntraDayDataLoader<OrderBook> {
    public CSVIndexFutureOrderBookProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        super(dataURL_, secs_, dates_);
    }

    @Override
    public List<OrderBook> loadData(Security sec_, LocalDate date_) {
        String fileName = StringUtils.substringBefore(sec_.getSymbol(), ".") + ".csv";
        File dataFile = new File(dataURL + "/" + DateUtil.date2Str(date_) + "/" + fileName);
        List<OrderBook> obs = null;
        try {
            List<String> l = FileUtils.readLines(dataFile);
            //List<String> l = Files.lines(dataFile.toPath());
            obs = new ArrayList<>(l.size() - 1);
            double lp = Double.MAX_VALUE, hp = Double.MIN_VALUE;

            for(int i = 1; i < l.size(); i++) {
                String[] ss = StringUtils.split(l.get(i), ',');
                List<Quote> asks = new ArrayList<>(1);
                List<Quote> bids = new ArrayList<>(1);
                asks.add(new Quote(parseDouble(ss[5]), parseDouble(ss[6])));
                bids.add(new Quote(parseDouble(ss[3]), parseDouble(ss[4])));
                double cp = parseDouble(ss[1]);
                double op = obs.size() == 0 ? cp : obs.get(0).getLastPx();
                lp = Math.min(lp, cp);
                hp = Math.max(hp, cp);
                double vol = parseDouble(ss[2]);
                double preSettle = parseDouble(ss[9]);
                double preCls = parseDouble(ss[10]);
                if(preSettle == 0 || preCls == 0 || cp == 0) {
                    throw new IllegalDataException("price is o on date " + date_);
                }

                //todo: changed to be preSettle
                LocalTime lt = DateUtil.getTime2(ss[0].length() == 8 ? "0" + ss[0] : ss[0]);
                OrderBook ob = OrderBook.of(asks, bids, cp, sec_, -1, vol, preCls,
                        op, cp, hp, lp, LocalDateTime.of(date_, lt));
                ob.setPreSettle(preSettle);
                obs.add(ob);
            }
        } catch (IOException e_) {
            if(obs != null) obs.clear();
            throw new IllegalDataException(e_);
        }

        return obs;
    }
}
