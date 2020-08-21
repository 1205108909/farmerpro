package com.phenix.farmer.signal;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotSupportedException;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.time.LocalTime;

public class KLineComputationSignalConfig implements IEngineConfig {
    public final static KLineComputationSignalConfig DEFAULT = new KLineComputationSignalConfig();

    static {
        DEFAULT.readConfig(Util.readStrAsXml("<Data enable='true' timeFrom='09:25:00000' timeTo='15:00:00000'/>"));
    }

    @Getter
    private String strRepresentation;
    @Getter
    private boolean enable;
    @Getter
    private LocalTime timeFrom;
    @Getter
    private LocalTime timeTo;

    @Override
    public boolean readConfig(Element e_) {
        strRepresentation = new XMLOutputter().outputString(e_);
        enable = Boolean.parseBoolean(e_.getAttributeValue("enable"));
        timeFrom = DateUtil.getTime(e_.getAttributeValue("timeFrom"));
        timeTo = DateUtil.getTime(e_.getAttributeValue("timeTo"));
        return false;
    }

    @Override
    public int hashCode() {
        throw new NotSupportedException(getClass().getName() + " is not allowed to be used as hash key");
    }
}
