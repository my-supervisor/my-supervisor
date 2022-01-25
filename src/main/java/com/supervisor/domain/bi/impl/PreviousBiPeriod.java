package com.supervisor.domain.bi.impl;

import com.supervisor.sdk.time.Periodicity;
import com.supervisor.domain.bi.BiPeriod;

/**
 * Previous period.
 *
 * @since 0.2
 */
public final class PreviousBiPeriod extends BiPeriod.Wrap {

    /**
     * Ctor.
     * @param period Period
     * @param periodicity Periodicity
     */
    public PreviousBiPeriod(final BiPeriod period, final Periodicity periodicity) {
        super(
            new BiPeriodOfPeriodicity(
                period.begin().minusDays(1),
                periodicity
            )
        );
    }
}
