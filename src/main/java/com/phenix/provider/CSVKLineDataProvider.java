package com.phenix.provider;

import com.phenix.data.KLineData;
import com.phenix.data.Security;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.feature.Featurable;
import com.phenix.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVKLineDataProvider extends AbstractIntraDayDataLoader<KLineData> {
    public CSVKLineDataProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        super(dataURL_, secs_, dates_);
    }

    //feature type, obi/cashflow
    public abstract Class<? extends Featurable> getKDataType();

    @Override
    public List<KLineData> loadData(Security sec_, LocalDate date_) {
        String fileName = sec_.getSymbol() + ".csv";
        File dataFile = new File(dataURL + "/" + DateUtil.date2Str(date_) + "/" + fileName);
        List<KLineData> obs = null;
        try {
            List<String> l = FileUtils.readLines(dataFile);
            obs = new ArrayList<>(l.size() - 1);
            int duration = -1;
            for(int i = 1; i < l.size(); i++) {
                String[] ss = StringUtils.split(l.get(i), ',');
                LocalTime lt = LocalTime.parse(ss[0].length() == 8 ? "0" + ss[0] : ss[0], DateUtil.TIME_HHMMSS);
                if(duration == -1) {
                    duration = getDurationInSecs(l);
                }
                if(duration <= 0)
                    throw new IllegalDataException("KLine Duration should > 0, 10/30/60 etc");
                LocalTime end = lt.plusSeconds(duration);
                obs.add(new KLineData(sec_, date_, lt, end, parseDouble(ss[1]), parseDouble(ss[2]),
                        parseDouble(ss[3]), parseDouble(ss[4]), parseDouble(ss[5]),
                        parseDouble(ss[6]), parseDouble(ss[7]), duration, getKDataType()));
            }

        } catch (IOException e_) {
            if(obs != null) obs.clear();
            throw new IllegalDataException(e_);
        }

        return obs;
    }

    private int getDurationInSecs(List<String> l_) {
        if(l_.size() >= 3) {
            String[] ss = StringUtils.split(l_.get(1), ',');
            String[] ss2 = StringUtils.split(l_.get(2), ',');
            LocalTime lt = LocalTime.parse(ss[0].length() == 8 ? "0" + ss[0] : ss[0], DateUtil.TIME_HHMMSS);
            LocalTime lt2 = LocalTime.parse(ss2[0].length() == 8 ? "0" + ss2[0] : ss2[0], DateUtil.TIME_HHMMSS);
            return (int) DateUtil.tradingTimeDiffInSecs(lt.toSecondOfDay(), lt2.toSecondOfDay());
        }
        return -1;
    }
}