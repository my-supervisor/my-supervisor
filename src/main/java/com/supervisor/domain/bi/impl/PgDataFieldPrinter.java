package com.supervisor.domain.bi.impl;

import com.supervisor.domain.DataField;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Params;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.DataFieldPrinter;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class PgDataFieldPrinter implements DataFieldPrinter {

	private final DataField field;
	private final String name;
	private final Params params;
	private final BiPeriod period;
	
	public PgDataFieldPrinter(final DataField field, final Params params, final BiPeriod period) {
		this(StringUtils.EMPTY, field, params, period);
	}
	
	public PgDataFieldPrinter(final String name, final DataField field, final Params params, final BiPeriod period) {
		this.name = name;
		this.field = field;
		this.params = params;
		this.period = period;
	}
	
	@Override
	public String toText() throws IOException {
		
		final String text;
		
		switch (field.style()) {
			case SIMPLE:
			case LIST:	
				text = new PgEditableDataFieldPrinter(
						    name,
							(EditableDataField)field
						).toText();
				break;
			case PARAMETER:
				text = new PgParamDataFieldPrinter(
						    name,
							(ParamDataField)field,
							params
						).toText();
				break;
			case FORMULAR:
				text = new PgFormularDataFieldPrinter(
						    name,
							(FormularDataField)field,
							params,
							period
						).toText();
				break;
			default:
				throw new IllegalArgumentException(String.format("DataField printer : style %s not supported !", field.style().toString()));
		}
		
		return text;
	}

}
