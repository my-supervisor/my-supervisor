package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataFieldValue;

public final class DataFieldValueImpl implements DataFieldValue {

	private final DataFieldType type;
	private final String value;
	
	public DataFieldValueImpl(final DataField field, final String value) throws IOException {
		this(field.type(), value);
	}

	public DataFieldValueImpl(final DataFieldType type, final String value) {
		
		this.type = type;
		this.value = value;
	}
	
	@Override
	public boolean isValid() {
		
		if(StringUtils.isBlank(cleaned()))
			return true;
		
		boolean isValid = true;
		
		switch (type) {
			case NUMBER:
				try {
					Double.parseDouble(cleaned());
				} catch (Exception e) {
					isValid = false;
				}
				
				break;
			case STRING:
				// nothing to test
				break;
			case BOOLEAN:
				try {
					Boolean.parseBoolean(cleaned());
				} catch (Exception e) {
					isValid = false;
				}
				
				break;
			case DATE:
				try {
					LocalDate.parse(cleaned(), DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (Exception e) {
					isValid = false;
				}
				
				break;
			default:
				break;
		}
		
		return isValid;
	}

	@Override
	public void validate() {
		
		if(!isValid()) {
			switch (type) {
				case NUMBER:
					try {
						Double.parseDouble(value);
					} catch (Exception e) {
						throw new IllegalArgumentException(String.format("La valeur %s n'est pas un nombre !", value));
					}
					break;
				case BOOLEAN:
					try {
						Boolean.parseBoolean(value);
					} catch (Exception e) {
						throw new IllegalArgumentException(String.format("La valeur %s n'est pas un booléen !", value));
					}
					break;
				case DATE:
					try {
						LocalDate.parse(value);
					} catch (Exception e) {
						throw new IllegalArgumentException(String.format("La valeur %s n'est pas une date !", value));
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public String cleaned() {
		
		if(value == null)
			return null;
		
		String cleaned = StringUtils.trim(value);
				
		if(type == DataFieldType.BOOLEAN) {
			String upperValue = value.toUpperCase();
			List<String> trueAcceptedValues = Arrays.asList("TRUE", "1", "YES", "OUI", "VRAI", "OK");
			List<String> falseAcceptedValues = Arrays.asList("FALSE", "0", "NO", "NON", "FAUX", "KO");
			
			if(trueAcceptedValues.contains(upperValue))
				cleaned = "true";
			else if(falseAcceptedValues.contains(upperValue))
				cleaned = "false";
			else
				cleaned = "true";
		}
		
		return cleaned;
	}

}
