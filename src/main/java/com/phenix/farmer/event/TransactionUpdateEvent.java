package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.orderbook.Transaction;
import lombok.Getter;

public class TransactionUpdateEvent extends AbstractEngineEvent {
    @Getter
    private Transaction transaction;

    public TransactionUpdateEvent(Transaction book_) {
        transaction = book_;
        consumer = FarmerController.getInstance().getTransactionUpdateEventConsumer();
    }

    public static TransactionUpdateEvent of(Transaction t_) {
        return new TransactionUpdateEvent(t_);
    }

    @Override
    public int hashCode() {
        return transaction.getSecurity().getBasketID();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), transaction.getSecurity().toString());
    }
}
