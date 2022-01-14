package com.minlessika.supervisor.domain.impl;

import java.util.List;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldGroup;

public final class DataFieldGroupImpl implements DataFieldGroup {

	private final int level;
	private final List<DataField> items;
	
	public DataFieldGroupImpl(final int level, final List<DataField> items) {
		this.level = level;
		this.items = items;
	}
	
	@Override
	public int level() {
		return level;
	}

	@Override
	public List<DataField> items() {
		return items;
	}

}
