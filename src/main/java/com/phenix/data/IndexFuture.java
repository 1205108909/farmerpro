package com.phenix.data;

import lombok.Getter;

import java.io.Serializable;

public class IndexFuture extends Security implements Serializable, Basketable {
    public IndexFuture(String symbol_, SecurityType type_, TradeStatus tradeStatus_, double multiplier_, double marginRate_, Security underlyingSec_) {
        super(symbol_, type_, tradeStatus_, Exchange.CFFEX, symbol_);
        this.multiplier = multiplier_;
        this.marginRate = marginRate_;
        this.underlyingSec = underlyingSec_;
    }

    public final static IndexFuture INDEX_FUTURE_IF1904 = new IndexFuture("if1904.ife", SecurityType.INDEX_FUTURE, TradeStatus.TRADABLE,
            300, 0.1, Security.INDEX_HS300);

    @Getter
    private double multiplier;
    @Getter
    private double marginRate;
    @Getter
    private Security underlyingSec;

    @Override
    public int getBasketID() {
        return underlyingSec.hashCode();
    }

    /**
     @Override public boolean equals(Object obj_) {
     if (obj_ == null) {
     return false;
     }
     if (obj_ == this) {
     return true;
     }
     if (obj_.getClass() != getClass()) {
     return false;
     }
     IndexFuture rhs = (IndexFuture) obj_;
     return super.equals(rhs) && rhs.underlyingSec.equals(rhs.underlyingSec);
     }

     @Override public int hashCode() {
     return Objects.hash(underlyingSec.getType(), underlyingSec.getSymbol());
     }
     */
}
