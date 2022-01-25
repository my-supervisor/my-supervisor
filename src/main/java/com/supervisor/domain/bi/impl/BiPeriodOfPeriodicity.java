package com.supervisor.domain.bi.impl;

import java.time.LocalDate;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.domain.bi.BiPeriod;

/**
 * BI Period based on Periodicity.
 *
 * @since 0.2
 */
public final class BiPeriodOfPeriodicity extends BiPeriod.Wrap {

    /**
     * Ctor.
     * @param today Today
     * @param periodicity Periodicity
     */
    public BiPeriodOfPeriodicity(final LocalDate today, final Periodicity periodicity) {
        super(
            new SimpleBiPeriod(
                periodicity.begin(today),
                today,
                periodicity.end(today)
            )
        );
    }
}
