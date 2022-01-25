package com.supervisor.domain.bi;

import java.time.LocalDate;

/**
 * Period used for BI.
 *
 * @since 0.2
 */
public interface BiPeriod {

    /**
     * Begin date.
     * @return Date
     */
    LocalDate begin();

    /**
     * End date.
     * @return Date
     */
    LocalDate end();

    /**
     * Today.
     * @return Date
     */
    LocalDate today();

    abstract class Wrap implements BiPeriod {

        private final BiPeriod period;

        public Wrap(final BiPeriod period) {
            this.period = period;
        }

        @Override
        public LocalDate begin() {
            return this.period.begin();
        }

        @Override
        public LocalDate end() {
            return this.period.end();
        }

        @Override
        public LocalDate today() {
            return this.period.today();
        }
    }

    BiPeriod EMPTY = new BiPeriod() {
        @Override
        public LocalDate begin() {
            return null;
        }

        @Override
        public LocalDate end() {
            return null;
        }

        @Override
        public LocalDate today() {
            return null;
        }
    };
}
