package com.minlessika.supervisor.domain.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;

import com.minlessika.supervisor.domain.DataFieldType;

public final class DefaultDataFieldValue {

	private final DataFieldType type;
	
	public DefaultDataFieldValue(DataFieldType type) {
		this.type = type;
	}

	public Object objectValue() {
		Object none;
		switch (type) {
			case BOOLEAN:
				none = true;
				break;
			case DATE:
				none = LocalDate.MIN;
				break;
			case NUMBER:
				none = 0.0;
				break;
			default:
				none = StringUtils.EMPTY;
				break;
		}
		
		return none;
	}

	public String stringValue() {
		String none;
		switch (type) {
			case BOOLEAN:
				none = "true";
				break;
			case DATE:
				none = LocalDate.MIN.format(DateTimeFormatter.ISO_LOCAL_DATE);
				break;
			case NUMBER:
				none = "0";
				break;
			default:
				none = "''";
				break;
		}
		
		return none;
	}

}
