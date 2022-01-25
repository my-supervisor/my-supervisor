package com.supervisor.domain.bi.impl;

import com.supervisor.sdk.time.Periodicity;
import com.supervisor.domain.bi.BiPeriod;

/**
 * Next period.
 *
 * @since 0.2
 */
public final class NextBiPeriod extends BiPeriod.Wrap {

    /**
     * Ctor.
     * @param period Period
     * @param periodicity Periodicity
     */
    public NextBiPeriod(final BiPeriod period, final Periodicity periodicity) {
        super(
            new BiPeriodOfPeriodicity(
                periodicity.end(period.end().plusDays(1)),
                periodicity
            )
        );
    }
}
