package com.supervisor.domain.bi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.supervisor.domain.bi.BiResult;
import com.supervisor.domain.bi.BiRow;
import com.supervisor.domain.bi.BiValue;

public final class BiRowImpl implements BiRow {

	final BiResult result;
	final List<BiValue> cells;
	
	public BiRowImpl(final BiResult result) {
		this.result = result;
		this.cells = new ArrayList<>();
	}
	
	@Override
	public BiResult result() {
		return result;
	}

	@Override
	public List<BiValue> cells() {
		return cells;
	}

	@Override
	public BiValue addCell(String name, Object value) {
		final BiValue biValue = new BiValueImpl(this, name, value);
		cells.add(biValue);
		return biValue;
	}

	@Override
	public BiValue get(String name) {
		
		final Optional<BiValue> value = cells.stream().filter(c -> c.name().equals(name)).findFirst();
		if(!value.isPresent())
			throw new IllegalArgumentException(String.format("Bi value with name %s not present !", name));
		
		return value.get();
	}

}
