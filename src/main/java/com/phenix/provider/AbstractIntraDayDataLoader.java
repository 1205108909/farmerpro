package com.phenix.provider;

import com.google.common.collect.ImmutableList;
import com.phenix.data.Security;
import com.phenix.util.Util;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractIntraDayDataLoader<T> implements IIntraDayDataProvider<T> {
    protected final ImmutableList<LocalDate> tradingDays;
    protected final ImmutableList<Security> securities;
    protected ProviderStatus status;
    protected final File dataURL;
    protected final Map<LocalDate, Map<Security, List<T>>> data;

    protected AbstractIntraDayDataLoader(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        this.dataURL = new File(dataURL_);
        this.status = ProviderStatus.Unknown;
        this.tradingDays = ImmutableList.copyOf(dates_);
        this.securities = ImmutableList.copyOf(secs_);
        this.data = new HashMap<>();
    }

    @Override
    public void connect(long timeout_) {
        status = ProviderStatus.Connecting;

        if(dataURL.exists()) {
            status = ProviderStatus.Connected;
        } else {
            status = ProviderStatus.Disconnected;
        }
    }

    @Override
    public void disconnect() {
        status = ProviderStatus.Disconnected;
    }

    @Override
    public boolean isConnected() {
        return ProviderStatus.Connected == status;
    }

    @Override
    public String getDescription() {
        return getClass().getName() + " and load data from " + dataURL.getAbsolutePath();
    }

    @Override
    public ProviderStatus getStatus() {
        return status;
    }

    @Override
    public List<LocalDate> getTradingDays() {
        return tradingDays;
    }

    @Override
    public List<Security> getSecurities() {
        return securities;
    }

    @Override
    public List<T> getData(Security sec_, LocalDate date_) {
        Map<Security, List<T>> m = data.get(date_);

        if(m == null) {
            m = new HashMap<>();
            data.put(date_, m);
        }

        List<T> l = m.get(sec_);
        if(l == null) {
            l = loadData(sec_, date_);
            m.put(sec_, l);
        }

        return l;
    }

    @Override
    public void clear(LocalDate date_) {
        Map<Security, List<T>> m = data.get(date_);
        if (m != null) {
            for (List<T> l : m.values()) {
                l.clear();
            }
            m.clear();
        }
        data.remove(date_);
    }

    public abstract List<T> loadData(Security sec_, LocalDate date_);

    protected double parseDouble(String s_) {
        return Util.roundQtyNear4Digit(Double.parseDouble(s_));
    }
}
