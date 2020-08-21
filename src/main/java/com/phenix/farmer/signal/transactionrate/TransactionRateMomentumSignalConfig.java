package com.phenix.farmer.signal.transactionrate;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotSupportedException;
import com.phenix.farmer.signal.OrderGenerationSignalConfig;
import com.phenix.farmer.signal.orderflow.OrderFlowMomentumSignalConfig;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.time.LocalTime;

public class TransactionRateMomentumSignalConfig extends OrderGenerationSignalConfig implements IEngineConfig {
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
    @Getter
    private LocalTime timeClosePosition;
    @Getter
    private LocalTime timeStopOpenPosition;
    @Getter
    private double pxChgCap2ClearPosition;
    @Getter
    private int numberOfTicksHitFar;
    @Getter
    private int orderPlacementTimeThresholdInSecs;
    @Getter
    private int maxNumberOfOrderPlacement;

    @Override
    public boolean readConfig(Element e_) {
        strRepresentation = new XMLOutputter().outputString(e_);
        enable = Boolean.parseBoolean(e_.getAttributeValue("enable"));
        timeFrom = DateUtil.getTime(e_.getAttributeValue("timeFrom"));
        timeTo = DateUtil.getTime(e_.getAttributeValue("timeTo"));
        rtFeatureTimeFrom = DateUtil.getTime(e_.getAttributeValue("rtFeatureTimeFrom"));
        timeClosePosition = DateUtil.getTime(e_.getAttributeValue("timeClosePosition"));
        timeStopOpenPosition = DateUtil.getTime(e_.getAttributeValue("timeStopOpenPosition"));
        pxChgCap2ClearPosition = Double.parseDouble(e_.getAttributeValue("pxChgCap2ClearPosition"));
        numberOfTicksHitFar = Integer.parseInt(e_.getAttributeValue("numberOfTicksHitFar"));
        orderPlacementTimeThresholdInSecs = Util.evaluateAsInt(e_.getAttributeValue("orderPlacementTimeThresholdInSecs"));
        maxNumberOfOrderPlacement = Integer.parseInt(e_.getAttributeValue("maxNumberOfOrderPlacement"));
        return false;
    }

    @Override
    public int hashCode() {
        throw new NotSupportedException(getClass().getName() + " is not allowed to be used as hash key");
    }

    public final static OrderFlowMomentumSignalConfig DEFAULT = new OrderFlowMomentumSignalConfig();
    static {
        DEFAULT.readConfig(Util.readStrAsXml("<Data enable='true' timeFrom='09:25:00000' timeTo='15:00:00000' rtFeatureTimeFrom='09:31:00000' " +
                "timeClosePosition='14:50:00000' timeStopOpenPosition='14:40:00000' " +
                "pxChgCap2ClearPosition='0.095' orderPlacementTimeThresholdInSecs='6 * 60' maxNumberOfOrderPlacement='6' numberOfTicksHitFar='0'/>"));
    }
}
