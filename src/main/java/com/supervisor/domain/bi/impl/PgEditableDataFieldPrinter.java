package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.bi.DataFieldPrinter;
import com.supervisor.domain.impl.PgTypeOfDataFieldType;
import com.supervisor.sdk.pgsql.PgBaseScheme;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.EditableDataField;

public final class PgEditableDataFieldPrinter implements DataFieldPrinter {

	private final EditableDataField field;
	private final String name;
	
	public PgEditableDataFieldPrinter(final EditableDataField field) {
		this(StringUtils.EMPTY, field);
	}
	
	public PgEditableDataFieldPrinter(final String name, final EditableDataField field) {
		this.name = name;
		this.field = field;
	}
	
	@Override
	public String toText() throws IOException {
		
		final String code = field.code();
		
		final String alias;		
		if(StringUtils.isBlank(name))
			alias = String.format("\"%s\"", code);
		else
			alias = name;
		
		final String codeFieldName;
		final String valueFieldName;
		
		if(field.style() == DataFieldStyle.LIST && field.dependencies().any()) {
			codeFieldName = "code";
			valueFieldName = "value";
		} else {
			codeFieldName = "df.code";
			valueFieldName = "mdfs.value";
		}
		
		return String.format(
				"(case\r\n" + 
				"       when %s = '%s' then %s \r\n" + 
				"       else (\r\n" + 
				"		select omdfs.value \r\n" + 
				"		from %s as omdfs\r\n" + 
				"		left join %s as odf on odf.id = omdfs.origin_field_id\r\n" + 
				"		where odf.code = '%s' and omdfs.sheet_id=sheet.id\r\n" + 
				"       )\r\n" + 
				"end)::%s as %s \r\n",
				codeFieldName,
				code,
				valueFieldName,
				PgBaseScheme.nameOfClazz(DataFieldOfSheet.class),
				PgBaseScheme.nameOfClazz(DataField.class),
			    code,
			    new PgTypeOfDataFieldType(field.type()).name(),
			    alias					    
	    );
	}

}
