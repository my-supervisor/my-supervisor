package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.DataFieldPrinter;
import com.supervisor.domain.impl.PgTypeOfDataFieldType;
import org.apache.commons.lang.StringUtils;
import com.supervisor.domain.Params;
import com.supervisor.domain.FormularDataField;

public final class PgFormularDataFieldPrinter implements DataFieldPrinter {

	private final FormularDataField formular;
	private final String name;
	private final Params params;
	private final BiPeriod period;
	
	public PgFormularDataFieldPrinter(final FormularDataField formular, final Params params, final BiPeriod period) {
		this(StringUtils.EMPTY, formular, params, period);
	}
	
	public PgFormularDataFieldPrinter(final String name, final FormularDataField formular, final Params params, final BiPeriod period) {
		this.name = name;
		this.formular = formular;
		this.params = params;
		this.period = period;
	}
	
	@Override
	public String toText() throws IOException {
		
		String code = formular.code();
		
		final String alias;
		if(StringUtils.isBlank(name))
			alias = String.format("\"%s\"", code); 
		else
			alias = name;
		
		return String.format(
			"(%s)::%s as %s \r\n",
		    new PgFormularExpressionPrinter(formular.mainExpression(), params, period).toText(),
		    new PgTypeOfDataFieldType(formular.type()).name(),
		    alias					    
	    );
	}

}
