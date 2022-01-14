package com.minlessika.supervisor.domain.bi.impl;

import java.util.ArrayList;
import java.util.List;

import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.bi.BiColumn;
import com.minlessika.supervisor.domain.bi.BiColumns;

public final class BiColumnsImpl implements BiColumns {

	private final List<BiColumn> aggregateds;
	private final List<BiColumn> simples;
	
	public BiColumnsImpl() {
		this.simples = new ArrayList<>();
		this.aggregateds = new ArrayList<>();
	}
	
	@Override
	public List<BiColumn> items() {
		final List<BiColumn> items = new ArrayList<>();
		items.addAll(simples);
		items.addAll(aggregateds);
		return items;
	}

	@Override
	public boolean canGroup() {
		return !aggregateds.isEmpty();
	}

	@Override
	public void add(BiColumn column) {
		if(column.aggregate() == AggregateFunc.NONE)
			simples.add(column);
		else
			aggregateds.add(column);			
	}

}
