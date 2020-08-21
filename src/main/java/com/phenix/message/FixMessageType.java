package com.phenix.message;

public enum FixMessageType {
    ORDER_CANCEL_REJECT(10), ORDER_EXPIRED(11), NEW_ORDER_SINGLE(1);
    private int value;
    FixMessageType(int v_) {
        this.value = v_;
    }
}
