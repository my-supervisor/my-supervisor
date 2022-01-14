package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minlessika.supervisor.domain.bi.BiColumn;
import com.minlessika.supervisor.domain.bi.BiColumns;
import com.minlessika.supervisor.domain.bi.Printer;

public final class PgColumnsPrinter implements Printer {

	private final BiColumns columns;
	
	public PgColumnsPrinter(final BiColumns columns) {
		this.columns = columns;
	}
	
	@Override
	public String toText() throws IOException {
		
		final List<Printer> printers = new ArrayList<>();
		for (BiColumn column : columns.items()) {
			printers.add(new PgColumnPrinter(column));
		}
		
		return new PgSelectPrinter(printers).toText();
	}

}
