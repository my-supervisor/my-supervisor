package com.supervisor.domain.bi.impl;

import com.supervisor.domain.bi.BiRow;
import com.supervisor.domain.bi.BiValue;

public final class BiValueImpl implements BiValue {

	private final BiRow row;
	private final String name;
	private final Object value;
	
	public BiValueImpl(final BiRow row, final String name, final Object value) {
		this.row = row;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public BiRow row() {
		return row;
	}

	@Override
	public String name() {
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T value() {
		return (T)value;
	}

}
