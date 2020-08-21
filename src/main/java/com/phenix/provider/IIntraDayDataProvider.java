package com.phenix.provider;

import com.phenix.data.Security;

import java.time.LocalDate;
import java.util.List;

public interface IIntraDayDataProvider<T> {
    void connect(long timeout_);

    void disconnect();

    boolean isConnected();

    String getDescription();

    ProviderStatus getStatus();

    //all the trading days this provider provided
    List<LocalDate> getTradingDays();

    //all the symbols this provider provided
    List<Security> getSecurities();

    List<T> getData(Security sec_, LocalDate date_);

    void clear(LocalDate date_);
}
