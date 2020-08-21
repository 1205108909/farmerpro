package com.phenix.farmer.signal;

import lombok.Getter;

/**
 * Created by phenix on 2017/5/18.
 */
public enum SignalType {
    FutureIndexSpreadSignal(2000, "FIS"),
    KLineComputationSignal(3000, "KCS"),
    OrderFlowComputationSignal(3002, "OFL"),
    OrderFlowMomentumSignal(3003, "OFM"),
    TransactionRateComputationSignal(3004, "TRS"),
    TransactionRateMomentumSignal(3005, "TRM"),
    DummySignal(-9999, "DS"),
    UNKNOWN(-1, "UNKNOWN");

    @Getter
    private int value;//used as priority
    @Getter
    private String code;

    SignalType(int type_, String code_) {
        this.value = type_;
        this.code = code_;
    }

    public static SignalType getSignalByCode(String code_) {
        SignalType []sts = SignalType.values();
        for(SignalType t : sts) {
            if(t.getCode().equals(code_))
                return t;
        }

        return SignalType.UNKNOWN;
    }
}
