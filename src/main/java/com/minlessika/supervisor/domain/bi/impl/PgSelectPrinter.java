package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataLinkOperator;
import com.minlessika.supervisor.domain.bi.BiSelect;
import com.minlessika.supervisor.domain.bi.Printer;

public final class PgSelectPrinter implements Printer {

	private final List<Printer> printers;
	
	public PgSelectPrinter(final BiSelect select) throws IOException {
		this(printersOf(select));
	}
	
	public PgSelectPrinter(final List<Printer> printers) {
		this.printers = printers;
	}
	
	private static List<Printer> printersOf(final BiSelect select) throws IOException{
		
		final List<Printer> printers = new ArrayList<>();
		for (Entry<DataField, DataLinkOperator> entry : select.columns().entrySet()) {			
			
			final DataField field = entry.getKey();
			final DataLinkOperator operator = entry.getValue();
			
			final String script;
			if(operator == DataLinkOperator.NEGATIVE) {
				script = String.format("- \"%s\" as \"%s\"", field.code(), field.code());
			} else {
				script = String.format("\"%s\"", field.code());
			}

			printers.add(new SimplePrinter(script));
		}
		
		return printers;
	}
	
	@Override
	public String toText() throws IOException {
		
		String script = StringUtils.EMPTY;		
		
		if(printers.isEmpty()) { // ignore
			script = "1";
		} else {
			for (Printer printer : printers) {
				if(StringUtils.isBlank(script))
					script = printer.toText();
				else {
					String line = printer.toText();
					script = String.format("%s, %s", script, line);
				}
			}
		}
				
		return script;
	}

}
