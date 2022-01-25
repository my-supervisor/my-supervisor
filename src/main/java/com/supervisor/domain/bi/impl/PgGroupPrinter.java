package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.bi.BiColumn;
import com.supervisor.domain.bi.BiColumns;
import com.supervisor.domain.bi.Printer;
import org.apache.commons.lang.StringUtils;

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
