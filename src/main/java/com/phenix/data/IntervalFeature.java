package com.phenix.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class IntervalFeature extends IntervalStat {
    @Getter
    @Setter
    private double rhp;
    @Getter
    @Setter
    private double rlp;

    public IntervalFeature() {
        rhp = Double.MIN_VALUE;
        rlp = Double.MAX_VALUE;
    }

    public void compute(List<IntervalStat> stats_) {
        cleanup();
        for (IntervalStat is : stats_) {
            if (is.isReady()) {
                avg = (avg * getVol() + is.avg * is.vol) / (vol + is.vol);
                setRhp(Math.max(is.high, rhp));
                setRlp(Math.min(is.low, rlp));
                high = Math.max(is.avg, high);
                low = Math.min(is.avg, low);
                cls = is.avg;
                vol = vol + is.vol;
                if (!ready) {
                    open = is.avg;
                    ready = true;
                }
            }
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        rhp = Double.MIN_VALUE;
        rlp = Double.MAX_VALUE;
    }

    public double momentum() {
        if(isReady()) {
            return (cls - open) / open;
        } else {
            return Double.NaN;
        }
    }

    public final static IntervalFeature INVALID = new IntervalFeature();
    public final static FeatureFactory<IntervalFeature> DefaultFactory = () -> new IntervalFeature();
}
