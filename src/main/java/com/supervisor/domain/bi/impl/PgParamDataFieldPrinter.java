package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.bi.DataFieldPrinter;
import com.supervisor.domain.impl.PgTypeOfDataFieldType;
import com.supervisor.sdk.metadata.BaseType;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Params;

public final class PgParamDataFieldPrinter implements DataFieldPrinter {

	private final ParamDataField field;
	private final String name;
	private final Params params;
	
	public PgParamDataFieldPrinter(final ParamDataField field, final Params params) {
		this(StringUtils.EMPTY, field, params);
	}
	
	public PgParamDataFieldPrinter(final String name, final ParamDataField field, final Params params) {
		this.name = name;
		this.field = field;
		this.params = params;
	}
	
	@Override
	public String toText() throws IOException {
		
		final ParamDataField filteredField = params.get(field.id());
		final String code = filteredField.code();
		
		final String alias;		
		if(StringUtils.isBlank(name))
			alias = String.format("\"%s\"", code);
		else
			alias = String.format("\"%s\"", name);
		
		final DataFieldType type = filteredField.type();
		final String value = filteredField.value();
		BaseType pType = new PgTypeOfDataFieldType(type);
		if(type == DataFieldType.STRING || type == DataFieldType.DATE) {
			return String.format("'%s'::%s as %s", value, pType.name(), alias);
		}else {
			return String.format("%s::%s as %s", value, pType.name(), alias);
		}
	}

}
