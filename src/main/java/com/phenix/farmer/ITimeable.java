package com.phenix.farmer;

import java.time.LocalDateTime;
import java.util.Comparator;

public interface ITimeable {
    // used for sort
    Comparator<ITimeable> TimeComparator = (o1, o2) -> {
        if (o1 == o2)
            return 0;
        if (o1 == null)
            return -1;
        if (o2 == null)
            return +1;
        return o1.getDateTime().compareTo(o2.getDateTime());
    };
    // used for search from
    Comparator<ITimeable> TimeComparator2 = (o1, o2) -> {
        if (o1 == o2)
            return 0;
        if (o1 == null)
            return -1;
        if (o2 == null)
            return +1;
        return o1.getDateTime().compareTo(o2.getDateTime()) >= 0 ? 1 : -1;
    };
    // used for search to
    Comparator<ITimeable> TimeComparator3 = (o1, o2) -> {
        if (o1 == o2)
            return 0;
        if (o1 == null)
            return -1;
        if (o2 == null)
            return +1;
        return o1.getDateTime().compareTo(o2.getDateTime()) <= 0 ? -1 : 1;
    };

    static ITimeable defaultTimeable(LocalDateTime dt_) {
        return new DefaultTimeable(dt_);
    }

    LocalDateTime getDateTime();

    class DefaultTimeable implements ITimeable {
        private LocalDateTime dateTime;

        public DefaultTimeable(LocalDateTime dt_) {
            this.dateTime = dt_;
        }

        @Override
        public LocalDateTime getDateTime() {
            return this.dateTime;
        }
    }
}
