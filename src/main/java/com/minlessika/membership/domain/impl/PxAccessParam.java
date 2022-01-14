package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.AccessParamQuantifier;
import com.minlessika.membership.domain.AccessParamValueType;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class PxAccessParam extends DomainRecordable<AccessParam> implements AccessParam {
	
	public PxAccessParam(final Record<AccessParam> source) throws IOException {
		super(source);
	}

	@Override
	public Access access() throws IOException {
		Record<Access> rec = record.of(AccessParam::access);
		return new PxAccess(rec);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(AccessParam::code); 
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(AccessParam::name);
	}

	@Override
	public void update(String code, String name, String defaultValue, AccessParamQuantifier quantifier, String message) throws IOException {
		
		record.isRequired(AccessParam::name, name);	
		record.isRequired(AccessParam::code, code);
		record.isRequired(AccessParam::defaultValue, defaultValue);
		record.isRequired(AccessParam::message, message);
		
		record.mustCheckThisCondition(
				quantifier != AccessParamQuantifier.NONE, 
				"Vous devez spécifier le quantificateur du paramètre !"
		);
		
		record.mustBeUnique(AccessParam::code, code.toUpperCase(), AccessParam::access);
		
		record.entryOf(AccessParam::name, name)
			  .entryOf(AccessParam::code, code.toUpperCase())
			  .entryOf(AccessParam::defaultValue, defaultValue)
			  .entryOf(AccessParam::quantifier, quantifier) 
			  .entryOf(AccessParam::message, message) 
			  .update();
	}

	@Override
	public String defaultValue() throws IOException {
		return record.valueOf(AccessParam::defaultValue);
	}

	@Override
	public AccessParamValueType valueType() throws IOException {
		return record.valueOf(AccessParam::valueType);
	}

	@Override
	public boolean isValid(String value) throws IOException {
		boolean isValid = true;
		
		switch (valueType()) {
			case NUMBER:
				try {
					Double.parseDouble(value);
				} catch (Exception e) {
					isValid = false;
				}
				
				break;
			case STRING:
				// nothing to test
				break;
			case BOOLEAN:
				try {
					Boolean.parseBoolean(value);
				} catch (Exception e) {
					isValid = false;
				}
				
				break;
			case DATE:
				try {
					LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
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
	public AccessParamQuantifier quantifier() throws IOException {
		return record.valueOf(AccessParam::quantifier);
	}

	@Override
	public String message() throws IOException {
		return record.valueOf(AccessParam::message);
	}	
}
