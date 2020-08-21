package com.phenix.message;

import lombok.Getter;

import java.util.UUID;

public class OrderCancelRequest {
    @Getter
    private String origClOrdID;
    @Getter
    private String clClOrdID;

    public OrderCancelRequest(String origClOrdID) {
        this(origClOrdID, UUID.randomUUID().toString());
    }

    public OrderCancelRequest(String origClOrdID_, String clClOrdID_) {
        this.origClOrdID = origClOrdID_;
        this.clClOrdID = clClOrdID_;
    }

    @Override
    public boolean equals(Object obj_) {
        if (obj_ == null) {
            return false;
        }
        if (obj_ == this) {
            return true;
        }
        if (obj_.getClass() != getClass()) {
            return false;
        }
        OrderCancelRequest rhs = (OrderCancelRequest) obj_;
        return origClOrdID.equals(rhs.origClOrdID) && clClOrdID.equals(rhs.clClOrdID);
    }
}
