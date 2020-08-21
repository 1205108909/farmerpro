package com.phenix.data;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class Basket<T> {
    @Getter
    private String id;

    @Getter
    private List<T> entries;

    private Basket(List<T> entries_) {
        this.entries = entries_;
        this.id = UUID.randomUUID().toString();
    }

    public static <T>Basket<T> of(List<T> entries_) {
        return new Basket<>(entries_);
    }

    /*public static Basket of(Security sec_, double refPx_, double qty_) {
        List<BasketEntry> l = new ArrayList<>();
        l.add(new BasketEntry(sec_, refPx_, qty_));
        return new Basket(l);
    }*/

    /*public final static class BasketEntry {
        @Getter
        private Security sec;
        @Getter
        private double refPx;
        @Getter
        private double qty;
        @Getter
        @Setter
        private double cumQty;
        @Getter
        @Setter
        private OrderStatus status;

        private BasketEntry(Security sec_, double refPx_, double qty_) {
            this.sec = sec_;
            this.refPx = refPx_;
            this.qty = qty_;
        }
    }*/
}
