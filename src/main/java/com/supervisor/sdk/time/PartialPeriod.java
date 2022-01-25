package com.supervisor.sdk.time;

import java.time.LocalDate;

/**
 * This period will take begin and end date of the given periodicity.
 *
 * @since 0.2
 */
public class PartialPeriod extends Period.Wrap {

    /**
     * Ctor.
     * @param date Reference date
     * @param periodicity Periodicity
     */
    public PartialPeriod(final LocalDate date, final Periodicity periodicity) {
        super(
            new SimplePeriod(
                periodicity.begin(date),
                date
            )
        );
    }
}
