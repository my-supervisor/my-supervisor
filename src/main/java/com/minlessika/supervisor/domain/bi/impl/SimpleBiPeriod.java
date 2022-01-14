package com.minlessika.supervisor.domain.bi.impl;

import com.minlessika.supervisor.domain.bi.BiPeriod;
import java.time.LocalDate;

/**
 * Simple BI Period.
 *
 * @since 0.2
 */
public final class SimpleBiPeriod implements BiPeriod {

    /**
     * Begin date.
     */
    private final LocalDate begin;

    /**
     * Today.
     */
    private final LocalDate today;

    /**
     * End date.
     */
    private final LocalDate end;

    public SimpleBiPeriod(
        final LocalDate begin,
        final LocalDate today,
        final LocalDate end
    ) {
        this.begin = begin;
        this.today = today;
        this.end = end;
        if(this.today.compareTo(this.begin) < 0 || this.today.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(
                "Today date must be contained within the period !"
            );
        }
    }

    @Override
    public LocalDate begin() {
        return this.begin;
    }

    @Override
    public LocalDate end() {
        return this.end;
    }

    @Override
    public LocalDate today() {
        return this.today;
    }
}
