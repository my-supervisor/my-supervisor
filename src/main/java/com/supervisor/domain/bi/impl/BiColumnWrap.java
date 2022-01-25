package com.supervisor.domain.bi.impl;

import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.bi.BiColumn;

public class BiColumnWrap implements BiColumn {

	private final BiColumn origin;
	
	public BiColumnWrap(final BiColumn origin) {
		this.origin = origin;
	}
	
	@Override
	public String name() {
		return origin.name();
	}

	@Override
	public AggregateFunc aggregate() {
		return origin.aggregate();
	}

	@Override
	public String body() {
		return origin.body();
	}

}
