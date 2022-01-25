package com.supervisor.domain.impl;

import java.util.List;

import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelGroup;

public final class DataModelGroupImpl implements DataModelGroup {

	private final int level;
	private final List<DataModel> items;
	
	public DataModelGroupImpl(final int level, final List<DataModel> items) {
		this.level = level;
		this.items = items;
	}
	
	@Override
	public int level() {
		return level;
	}

	@Override
	public List<DataModel> items() {
		return items;
	}

}
