package com.supervisor.domain.bi.impl;

import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.domain.bi.BiColumns;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.BiRequest;
import com.supervisor.domain.bi.BiSelects;
import org.apache.commons.lang.StringUtils;
import java.time.LocalDate;

public final class BiRequestImpl implements BiRequest {

	private final String filter;

	/**
	 * Period.
	 */
	private final BiPeriod period;

	private final long start;
	private final int limit;
	private final Ordering ordering;
	private final BiSelects selects;
	private final BiColumns columns;
	
	public BiRequestImpl(final BiPeriod period) {
		this(StringUtils.EMPTY, period, 0L, 0, Ordering.EMPTY);
	}
	
	public BiRequestImpl(String filter, final BiPeriod period, long start, int limit, Ordering ordering) {
		this.filter = filter;
		this.period = period;
		this.start = start;
		this.limit = limit;
		this.ordering = ordering;
		this.selects = new BiSelectsImpl(this);
		this.columns = new BiColumnsImpl();
	}
	
	@Override
	public String filter() {
		return filter;
	}

	@Override
	public BiSelects selects() {
		return selects;
	}

	@Override
	public BiPeriod period() {
		return period;
	}

	@Override
	public long start() {
		return start;
	}

	@Override
	public int limit() {
		return limit;
	}

	@Override
	public Ordering ordering() {
		return ordering;
	}

	@Override
	public BiRequest of(LocalDate begin, LocalDate end) {
		return new BiRequestImpl(
			filter,
			new SimpleBiPeriod(begin, end, end),
			start,
			limit,
			ordering
		);
	}

	@Override
	public BiRequest to(LocalDate now) {
		return new BiRequestImpl(filter, period, start, limit, ordering);
	}

	@Override
	public BiRequest with(String filter) {
		return new BiRequestImpl(filter, period, start, limit, ordering);
	}

	@Override
	public BiRequest with(long start, int limit) {
		return new BiRequestImpl(filter, period, start, limit, ordering);
	}

	@Override
	public BiColumns columns() {
		return columns;
	}

}
