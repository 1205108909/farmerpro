package com.phenix.math;

import com.google.common.base.MoreObjects;
import com.phenix.util.DateUtil;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

/**
 *  Representing a single line by [node1, node2]
 */
public class TimedInterval implements Serializable, Comparable<TimedInterval> {
    @Getter
    protected LocalTime start;
    @Getter
    protected LocalTime end;

    public final static TimedInterval INVALID = new TimedInterval(LocalTime.MAX, LocalTime.MAX);

    public TimedInterval(LocalTime start_, LocalTime end_) {
        if(start_.isAfter(end_)) {
            throw new IllegalArgumentException("start " + start_ + " is after end " + end_);
        }
        this.start = start_;
        this.end = end_;
    }

    public boolean contains(LocalTime t_) {
        return contains(t_, true, true);
    }

    public boolean contains(LocalTime t_, boolean leftInclusive_, boolean rightInclusive_) {
        if(leftInclusive_ && rightInclusive_) {
            return !t_.isBefore(start) && !t_.isAfter(end);
        } else if (leftInclusive_ && !rightInclusive_) {
            return !t_.isBefore(start) && t_.isBefore(end);
        } else if (!leftInclusive_ && rightInclusive_) {
            return t_.isAfter(start) && !t_.isAfter(end);
        } else {
            return t_.isAfter(start) && t_.isBefore(end);
        }
    }

    public int getStartInSecs() {
        return start.toSecondOfDay();
    }

    public int getEndInSecs() {
        return end.toSecondOfDay();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("start:", start)
                .add("end", end)
                .toString();
    }

    @Override
    public boolean equals(Object obj_) {
        if (obj_ == null) {
            return false;
        }
        if (obj_ == this) {
            return true;
        }
        if (obj_.getClass() != getClass()) {
            return false;
        }
        TimedInterval rhs = (TimedInterval) obj_;

        return start.equals(rhs.start) && end.equals(rhs.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public int compareTo(TimedInterval other_) {
        int res = start.compareTo(other_.start);
        return res != 0 ? res : end.compareTo(end);
    }

    public long getDurationInSecs() {
        return INVALID == this ? 0 : DateUtil.tradingTimeDiffInSecs(start.toSecondOfDay(), end.toSecondOfDay());
    }
}
