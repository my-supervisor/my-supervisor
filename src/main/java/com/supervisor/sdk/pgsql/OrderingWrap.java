package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.Ordering;

public class OrderingWrap implements Ordering {
	
	private final Ordering origin;
	
	public OrderingWrap(final Ordering origin) {
		this.origin = origin;
	}

	@Override
	public String script() {
		return origin.script();
	}

	@Override
	public Ordering reverse() {
		return origin.reverse();
	}
}
