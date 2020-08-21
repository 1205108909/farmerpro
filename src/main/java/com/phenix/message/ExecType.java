package com.phenix.message;

import lombok.Getter;

public enum ExecType {
    CALCULATED('B'),
    CANCELED('4'),
    CANCELLED('4'),
    DONE_FOR_DAY('3'),
    EXPIRED('C'),
    FILL('2'),
    NEW('0'),
    ORDER_STATUS('I'),
    PARTIAL_FILL('1'),
    PENDING_CANCEL('6'),
    PENDING_CANCELREPLACE('6'),
    PENDING_NEW('A'),
    PENDING_REPLACE('E'),
    REJECTED('8'),
    REPLACE('5'),
    REPLACED('5'),
    RESTATED('D'),
    STOPPED('7'),
    SUSPENDED('9'),
    TRADE('F'),
    TRADE_CANCEL('H'),
    TRADE_CORRECT('G'),
    TRADE_HAS_BEEN_RELEASED_TO_CLEARING('K'),
    TRADE_IN_A_CLEARING_HOLD('J'),
    TRIGGERED_OR_ACTIVATED_BY_SYSTEM('L');

    @Getter
    private int value;

    ExecType(int value_) {
        value = value_;
    }
}