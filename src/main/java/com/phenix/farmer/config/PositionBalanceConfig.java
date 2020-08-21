package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import lombok.Value;
import org.jdom2.Element;

@Value
public class PositionBalanceConfig implements IEngineConfig {
    public final static PositionBalanceConfig DEFAULT = new PositionBalanceConfig(3_000_000);
    @Getter
    private double initialCash;

    public PositionBalanceConfig(double initialCash_) {
        this.initialCash = initialCash_;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
