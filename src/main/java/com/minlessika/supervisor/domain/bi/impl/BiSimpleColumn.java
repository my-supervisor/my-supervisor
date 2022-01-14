package com.minlessika.supervisor.domain.bi.impl;

import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.bi.BiColumn;

public final class BiSimpleColumn implements BiColumn {

	private final String name;
	private final String body;
	private final AggregateFunc aggregate;
	
	public BiSimpleColumn(final String name, final String body, final AggregateFunc aggregate) {
		this.name = name;
		this.body = body;
		this.aggregate = aggregate;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public AggregateFunc aggregate() {
		return aggregate;
	}

	@Override
	public String body() {
		return body;
	}

}
