package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import org.jdom2.Element;

public class FeatureConfig implements IEngineConfig {
    @Getter
    private String futureIndexSpread;
    @Getter
    private String kline;
    @Getter
    private String dailyFeature;
    @Getter
    private String universe;


    public FeatureConfig(String futureIndexSpread_, String kline_, String dailyFeature_, String universe_) {
        futureIndexSpread = futureIndexSpread_;
        kline = kline_;
        dailyFeature = dailyFeature_;
        universe = universe_;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
