package com.phenix.farmer.signal.transactionrate;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotSupportedException;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.time.LocalTime;

public class TransactionRateComputationSignalConfig implements IEngineConfig {
    @Getter
    private String strRepresentation;
    @Getter
    private boolean enable;
    @Getter
    private LocalTime timeFrom;
    @Getter
    private LocalTime timeTo;
    @Getter
    private LocalTime rtFeatureTimeFrom;

    @Override
    public boolean readConfig(Element e_) {
        strRepresentation = new XMLOutputter().outputString(e_);
        enable = Boolean.parseBoolean(e_.getAttributeValue("enable"));
        timeFrom = DateUtil.getTime(e_.getAttributeValue("timeFrom"));
        timeTo = DateUtil.getTime(e_.getAttributeValue("timeTo"));
        rtFeatureTimeFrom = DateUtil.getTime(e_.getAttributeValue("rtFeatureTimeFrom"));
        return false;
    }

    @Override
    public int hashCode() {
        throw new NotSupportedException(getClass().getName() + " is not allowed to be used as hash key");
    }

    public final static TransactionRateComputationSignalConfig DEFAULT = new TransactionRateComputationSignalConfig();
    static {
        DEFAULT.readConfig(Util.readStrAsXml("<Data enable='true' timeFrom='09:25:00000' timeTo='15:00:00000' rtFeatureTimeFrom='09:31:00000'/>"));
    }
}
