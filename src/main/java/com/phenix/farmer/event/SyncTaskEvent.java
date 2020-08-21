package com.phenix.farmer.event;

import com.google.common.base.MoreObjects;
import com.phenix.data.Security;
import com.phenix.exception.SyncTaskExecutionException;
import com.phenix.farmer.FarmerController;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SyncTaskEvent<T> extends AbstractEngineEvent {
    @Getter
    private FutureTask<T> task;
    @Getter
    private Security security;

    public SyncTaskEvent(FutureTask<T> task_, Security sec_) {
        Objects.requireNonNull(task_);
        task = task_;
        consumer = FarmerController.getInstance().getSyncTaskEventConsumer();
        security = sec_;
    }

    public T get() {
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e_) {
            throw new SyncTaskExecutionException(e_);
        }
    }

    public T get(long timeInSecs_) {
        try {
            return task.get(timeInSecs_, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e_) {
            throw new SyncTaskExecutionException(e_);
        }
    }


    @Override
    public int hashCode() {
        return security.getBasketID();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("security:", security)
                .toString();
    }
}
