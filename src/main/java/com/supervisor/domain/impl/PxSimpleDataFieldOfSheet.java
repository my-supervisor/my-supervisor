package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.SimpleDataFieldOfSheet;

public final class PxSimpleDataFieldOfSheet extends DataFieldOfSheetWrap implements SimpleDataFieldOfSheet {

	private final SimpleDataField field;
	
	public PxSimpleDataFieldOfSheet(DataFieldOfSheet origin) throws IOException {
		super(origin);
		
		this.field = (SimpleDataField)origin.origin(); 
	}

	@Override
	public String defaultValue() throws IOException {
		return field.defaultValue();
	}

	@Override
	public void defaultValue(String value) throws IOException {
		field.defaultValue();
	}

	@Override
	public boolean takeLastValue() throws IOException {
		return field.takeLastValue();
	}

	@Override
	public void takeLastValue(boolean takeLastValue) throws IOException {
		field.takeLastValue(takeLastValue);		
	}

	@Override
	public boolean mustEditOnce() throws IOException {
		return field.mustEditOnce();
	}

	@Override
	public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
		field.makeMustEditOnce(mustEditOnce);
	}

	@Override
	public boolean isReadOnly() throws IOException {
		return field.isReadOnly();
	}

	@Override
	public void makeReadOnly(boolean readOnly) throws IOException {
		field.makeReadOnly(readOnly);
	}

	@Override
	public void update(String code, String name, DataFieldType type, String description) throws IOException {
		field.update(code, name, type, description); 
	}

}
