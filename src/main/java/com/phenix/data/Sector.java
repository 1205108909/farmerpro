package com.phenix.data;

import lombok.Getter;

import java.util.Set;

/**
 * Created by LI JT on 2016/7/12.
 * Description: This class contains wind sector information, which includes SectorCode like 884001.WI, SectorName is wind, and
 * securities this sectors contains.
 */
public class Sector {

    @Getter
    private String sectorCode;
    @Getter
    private String sectorName;
    @Getter
    private Set<Security> securities;

    public Sector(String sectorCode_, String sectorName_, Set<Security> securities_) {
        sectorCode = sectorCode_;
        sectorName = sectorName_;
        securities = securities_;
    }

    public boolean contains(Security security) {
        return securities.contains(security);
    }
}
