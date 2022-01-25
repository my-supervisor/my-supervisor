package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.bi.BiColumn;
import com.supervisor.domain.bi.Printer;

public final class PgColumnPrinter implements Printer {

	private final BiColumn column;
	
	public PgColumnPrinter(final BiColumn column) {
		this.column = column;
	}
	
	@Override
	public String toText() throws IOException {
		
		AggregateFunc aggregate = AggregateFunc.NONE;
		if(column.aggregate() != AggregateFunc.NONE && column.aggregate() != AggregateFunc.FIRST_VALUE && column.aggregate() != AggregateFunc.LAST_VALUE) {
			aggregate = column.aggregate();
		}
		
		String body;
		if(!column.name().equals(column.body()))
			body = column.body();
		else
			body = column.name();
		
		if(aggregate != AggregateFunc.NONE) {
			body = PgDataWarehouse.aggregateColumn(
						body, 
						DataFieldType.NONE, 
						aggregate
				   );
		}
		
		if(!column.name().equals(body))
			return String.format("%s as %s", body, column.name());
		else
			return body;
	}

}
