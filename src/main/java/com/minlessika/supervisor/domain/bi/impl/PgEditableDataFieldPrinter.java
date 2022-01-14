package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.pgsql.PgBaseScheme;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.bi.DataFieldPrinter;
import com.minlessika.supervisor.domain.impl.PgTypeOfDataFieldType;

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
