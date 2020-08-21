package com.phenix.provider;

import com.phenix.data.Security;
import com.phenix.farmer.feature.Featurable;
import com.phenix.farmer.feature.OrderBookImbanlanceStat;

import java.time.LocalDate;
import java.util.List;

public class OBIKLineDataProvider extends CSVKLineDataProvider {
    public OBIKLineDataProvider(String dataURL_, List<Security> secs_, List<LocalDate> dates_) {
        super(dataURL_, secs_, dates_);
    }

    @Override
    public Class<? extends Featurable> getKDataType() {
        return OrderBookImbanlanceStat.class;
    }
}
