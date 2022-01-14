package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.bi.BiColumn;
import com.minlessika.supervisor.domain.bi.BiColumns;
import com.minlessika.supervisor.domain.bi.Printer;

public final class PgGroupPrinter implements Printer {

	private final BiColumns columns;
	
	public PgGroupPrinter(final BiColumns columns) {
		this.columns = columns;
	}
	
	@Override
	public String toText() throws IOException {
		
		String script = StringUtils.EMPTY;		
		
		if(columns.canGroup()) {
			for (BiColumn column : columns.items()) {
				
				if(column.aggregate() == AggregateFunc.NONE) {
					if(StringUtils.isBlank(script))
						script = String.format("group by %s", column.name());
					else {
						script = String.format("%s, %s", script, column.name());
					}
				}
			}
		}
				
		return script;
	}

}
