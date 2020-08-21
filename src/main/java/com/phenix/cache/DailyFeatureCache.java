package com.phenix.cache;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.phenix.data.Security;
import com.phenix.exception.CacheOperationException;
import com.phenix.exception.NotSupportedException;
import com.phenix.farmer.RunningMode;
import com.phenix.farmer.feature.*;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author phenix
 * @description
 *     params: the 1st is a folder
 *     hardcode feature file for local
 *          FutureIndexSpread -> spread.csv
 *          OrderBookImbanlance -> obi.csv
 */
public class DailyFeatureCache extends EngineCache<Class<? extends Featurable>, Table<Security, LocalDate, Featurable>> {
    @Override
    public void init(RunningMode mode_, Object... params_) {
        if(RunningMode.LOCAL == mode_) initLocal(params_);
        else initFromDB(params_);
    }

    public void initLocal(Object... params_) {
        String path = (String)params_[0];
        SecurityCache ca = (SecurityCache) params_[1];
        initFISpread(path, ca);
        initVolume(path, ca);
        initVolumeGreen(path, ca);
        initVolumeRed(path, ca);
    }

    private void initFISpread(String path_, SecurityCache sc_) {
        initFeature(path_, sc_, "spread", FutureIndexSpreadStat.class);
    }
    private void initVolume(String path_, SecurityCache sc_) {
        initFeature(path_, sc_, "volume", KLineVolStat.class);
    }
    private void initVolumeGreen(String path_, SecurityCache sc_) {
        initFeature(path_, sc_, "volume_green", GreenKLineVolStat.class);
    }
    private void initVolumeRed(String path_, SecurityCache sc_) {
        initFeature(path_, sc_, "volume_red", RedKLineVolStat.class);
    }

    private<T extends Featurable> void initFeature(String path_, SecurityCache sc_, String featureName_, Class<T> clazz_) {
        Table<Security, LocalDate, Featurable> table = HashBasedTable.create();
        String path = path_ + "/" + featureName_;
        File f = new File(path);
        for(File fi : f.listFiles()) {
            String s = StringUtils.substringBeforeLast(fi.getName(), ".");
            Security sec = sc_.getBySymbol(s);
            try {
                List<String> lines = Files.lines(fi.toPath()).skip(1).collect(Collectors.toList());
                int i = 0;
                for(String ll : lines) {
                    String []ss = ll.split(",");
                    LocalDate date = DateUtil.getDate(ss[0]);
                    T fs;
                    if(ss.length > 1) {// shouldn't happen in prod, there may be data missing for some day
                        double min = Double.parseDouble(ss[1]);
                        double q5 = Double.parseDouble(ss[2]);
                        double q10 = Double.parseDouble(ss[3]);
                        double q20 = Double.parseDouble(ss[4]);
                        double q30 = Double.parseDouble(ss[5]);
                        double q40 = Double.parseDouble(ss[6]);
                        double q50 = Double.parseDouble(ss[7]);
                        double q60 = Double.parseDouble(ss[8]);
                        double q70 = Double.parseDouble(ss[9]);
                        double q80 = Double.parseDouble(ss[10]);
                        double q90 = Double.parseDouble(ss[11]);
                        double q95 = Double.parseDouble(ss[12]);
                        double max = Double.parseDouble(ss[13]);
                        double mean = Double.parseDouble(ss[14]);
                        double std = Double.parseDouble(ss[15]);
                        fs = clazz_.getDeclaredConstructor(DailyFeatureStat.CLAZZ).newInstance(sec, date, LocalTime.MIN, q5, q10, q20, q30, q40, q50, q60,
                                q70, q80, q90, q95, min, max, mean, std);
                    } else
                        fs = Util.getValue(clazz_, "INVALID");
                    table.put(sec, date, fs);
                }
            }  catch (Exception e_) {
                throw new CacheOperationException(e_);
            }
        }
        T invalid = Util.getValue(clazz_, "INVALID");
        Table<Security, LocalDate, Featurable> t = Util.rollingBackOneDay(table, invalid);
        table.clear();
        put(clazz_, t);
    }

    public void initFromDB(Object... params_) {
        throw new NotSupportedException("unsupported yet");
    }

    @Override
    public void clear() {
        Collection<Table<Security, LocalDate, Featurable>> values = values();
        values.forEach(e -> e.clear());
        super.clear();
    }
}
