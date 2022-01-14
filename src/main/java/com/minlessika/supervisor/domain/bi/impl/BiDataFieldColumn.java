package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.bi.BiColumn;

public final class BiDataFieldColumn extends BiColumnWrap implements BiColumn {

	public BiDataFieldColumn(final DataField field) throws IOException {
		this(field.code(), field);
	}
	
	public BiDataFieldColumn(final String name, final DataField field) throws IOException {
		super(
			new BiSimpleColumn(
				String.format("\"%s\"", name), 
				String.format("\"%s\"", field.code()), 
				AggregateFunc.NONE
			)
		);
	}
}
