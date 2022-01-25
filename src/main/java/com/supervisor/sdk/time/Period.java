package com.supervisor.sdk.time;

import java.time.LocalDate;

public interface Period {

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
	 * Total number of days on period.
	 * @return Count
	 */
	long days();

	/**
	 * Total number of months on period.
	 * @return Count
	 */
	long months();

	/**
	 * Helper for decorating.
	 */
	abstract class Wrap implements Period {

		/**
		 * Period to wrap.
		 */
		private final Period origin;

		public Wrap(final Period origin) {
			this.origin = origin;
		}

		@Override
		public final LocalDate begin() {
			return this.origin.begin();
		}

		@Override
		public final LocalDate end() {
			return this.origin.end();
		}

		@Override
		public final long days() {
			return this.origin.days();
		}

		@Override
		public final long months() {
			return this.origin.months();
		}
	}
	
	Period EMPTY = new Period() {
		
		@Override
		public long months() {
			
			return 0L;
		}
		
		@Override
		public LocalDate end() {
			
			return null;
		}
		
		@Override
		public long days() {
			
			return 0L;
		}
		
		@Override
		public LocalDate begin() {
			
			return null;
		}
	};
}
