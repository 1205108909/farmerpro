package com.phenix.data;

public final class ObjectWrapper<T> {
    private T t;

    public ObjectWrapper(T t_) {
        t = t_;
    }

    public static <T>ObjectWrapper of(T t_) {
        return new ObjectWrapper(t_);
    }

    public final T get() {
        return t;
    }

    public final void set(T t_) {
        t = t_;
    }

    @Override
    public String toString() {
        return t.toString();
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }
}
