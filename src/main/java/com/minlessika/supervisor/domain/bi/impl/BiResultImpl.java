package com.minlessika.supervisor.domain.bi.impl;

import java.util.ArrayList;
import java.util.List;

import com.minlessika.supervisor.domain.bi.BiResult;
import com.minlessika.supervisor.domain.bi.BiRow;

public final class BiResultImpl implements BiResult {

	private final long count;
	private final List<BiRow> rows;
	
	public BiResultImpl(final long count) {
		this.count = count;
		this.rows = new ArrayList<>();
	}
	
	@Override
	public List<BiRow> rows() {
		return rows;
	}

	@Override
	public long count() {
		return count;
	}

	@Override
	public BiRow addRow() {
		final BiRow row = new BiRowImpl(this);
		rows.add(row);
		return row;
	}

}
