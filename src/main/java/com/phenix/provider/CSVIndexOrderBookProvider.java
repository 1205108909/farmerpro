package com.phenix.provider;

import com.phenix.data.Security;
import com.phenix.exception.IllegalDataException;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import com.phenix.orderbook.OrderBook;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVIndexOrderBookProvider extends AbstractIntraDayDataLoader<OrderBook> {
    public CSVIndexOrderBookProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
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

            for(int i = 1; i < l.size(); i++) {
                String[] ss = StringUtils.split(l.get(i), ',');

                LocalTime lt = DateUtil.getTime2(ss[0].length() == 8 ? "0" + ss[0] : ss[0]);
                obs.add(OrderBook.of(Collections.emptyList(), Collections.emptyList(), parseDouble(ss[1]),
                        sec_, parseDouble(ss[3]), parseDouble(ss[2]), parseDouble(ss[9]),
                        parseDouble(ss[6]), parseDouble(ss[1]), parseDouble(ss[7]),
                        parseDouble(ss[8]), LocalDateTime.of(date_, lt)));
            }

        } catch (IOException e_) {
            if(obs != null) obs.clear();
            throw new IllegalDataException(e_);
        }

        return obs;
    }
}