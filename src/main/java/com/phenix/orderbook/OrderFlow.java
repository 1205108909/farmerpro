package com.phenix.orderbook;

import com.phenix.data.OrderSide;
import com.phenix.data.OrderType;
import com.phenix.data.Security;
import com.phenix.farmer.ITimeable;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Keep orderKind and functionCode for now
 */
public class OrderFlow extends TimeSeriesSecurityData implements IEvaluationData, Serializable, Cloneable, ITimeable {
    private static final long serialVersionUID = 5661552489004666908L;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private double qty;
    @Getter
    @Setter
    private String orderKind;
    @Getter
    @Setter
    private int orderNo;
    @Getter
    @Setter
    private String functionCode;
    @Setter
    private LocalDateTime dateTime;
    @Getter
    @Setter
    private Security security;
    @Getter
    private OrderSide side;
    @Getter
    private OrderType type;

    @Override
    public boolean equals(Object obj_) {
        return EqualsBuilder.reflectionEquals(this, obj_);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException(String.format("%s can not be used as key in hash", Transaction.class));
    }

    @Override
    public Object clone() {
        return Util.deepCopy(this);
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }


    private OrderFlow(double px_, double vol_, String orderKind_, int orderNo_, String functionCode_, LocalDateTime dt_, Security sec_) {
        price = px_;
        qty = vol_;
        orderKind = orderKind_;
        orderNo = orderNo_;
        functionCode = functionCode_;
        dateTime = dt_;
        security = sec_;
        side = OrderSide.parse(functionCode_);
        type = OrderType.parse(orderKind_);
    }

    public String getSymbol() {
        return security.getSymbol();
    }

    public static OrderFlow of(LocalDateTime time_) {
        return of(-1, -1, "N", -1, "N", time_, null);
    }

    public static OrderFlow of(double px_, double vol_, String orderKind_, int orderNo_, String functionCode_, LocalDateTime dt_, Security sec_) {
        return new OrderFlow(px_, vol_, orderKind_, orderNo_, functionCode_, dt_, sec_);
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isValid() {
        return security != null && price != 0 && qty != 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("[security=").append(security.getSymbol())
                .append(", time=").append(dateTime.toLocalTime())
                .append(", price=").append(price)
                .append(", qty=").append(qty)
                .append(", type=").append(type)
                .append(", side=").append(side)
                .append(", orderNo=").append(orderNo)
                .append(", functionCode=").append(functionCode)
                .append(", orderKind=").append(orderKind)
                .append("]");
        return sb.toString();
    }
}
