package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;

public final class TypedDataField {
	
	private TypedDataField() {
		
	}
	
	public static DataField convert(Record<DataField> record) throws IOException {
		
		DataField item;
		
		DataFieldStyle style = record.valueOf(DataField::style);
		
		switch (style) {
			case STRUCTURE:
				item = new PxTableDataField(record);
				break;
			case LIST:
				item = new PxListDataField(record); 
				break;
			case SIMPLE:
				item = new PxSimpleDataField(record); 
				break;
			case FORMULAR:
				item = new PxFormularDataField(record); 
				break;
			case PARAMETER:
				item = new PxParamDataField(record); 
				break;
			default:
				throw new IllegalArgumentException(String.format("DataField of style %s not supported !", style.toString())); 
		}
		
		return item;
	}
}
