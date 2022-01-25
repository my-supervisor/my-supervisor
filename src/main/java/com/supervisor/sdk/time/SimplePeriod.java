package com.supervisor.sdk.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Simple period that simply uses given begin and end date.
 *
 * @since 0.2
 */
public final class SimplePeriod implements Period {

	/**
	 * Begin date.
	 */
	private final LocalDate begin;

	/**
	 * End date.
	 */
	private final LocalDate end;

	/**
	 * Ctor.
	 * @param begin Begin date
	 * @param end End date
	 */
	public SimplePeriod(final LocalDate begin, final LocalDate end) {
		this.begin = begin;
		this.end = end;
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
	public long days() {
		return ChronoUnit.DAYS.between(this.begin, this.end) + 1;
	}

	@Override
	public long months() {
		return ChronoUnit.MONTHS.between(this.begin, this.end) + 1;
	}
}
