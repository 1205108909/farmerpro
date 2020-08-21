package com.phenix.farmer.event;

import com.google.common.base.MoreObjects;
import com.phenix.data.Basketable;
import lombok.Getter;

/**
 * Created by yangfei on 2017/5/15.
 */
public class ObjectWrapperEvent<T> extends AbstractEngineEvent {
    @Getter
    private T t;

    public ObjectWrapperEvent(T t_) {
        t = t_;
    }

    public static <T> ObjectWrapperEvent of(T t_) {
        return new ObjectWrapperEvent<>(t_);
    }

    @Override
    public int hashCode() {
        return t instanceof Basketable ? ((Basketable) t).getBasketID() : t.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("t:", t)
                .toString();
    }
}
