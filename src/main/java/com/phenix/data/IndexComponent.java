package com.phenix.data;

import com.phenix.exception.NotSupportedException;
import lombok.Value;

@Value
public class IndexComponent {
    private Security index;
    private Security component;
    private double weight;

    @Override
    public int hashCode() {
        throw new NotSupportedException("FutureIndexSpreadStat is not allowed to be used as hash key");
    }
}
