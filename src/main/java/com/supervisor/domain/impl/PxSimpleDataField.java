package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;

public final class PxSimpleDataField extends PxEditableDataField implements SimpleDataField {
	
	private final Record<SimpleDataField> simpleRecord;
	
	public PxSimpleDataField(final Record<DataField> record) throws IOException {
		super(record);
		
		this.simpleRecord = record.listOf(SimpleDataField.class).get(record.id());
	}
	
	@Override
	protected void checkStyle(Record<DataField> record) throws IOException {
		if(record.valueOf(DataField::style) != DataFieldStyle.SIMPLE)
			throw new IllegalArgumentException("Field isn't a simple field !");
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		
		if(style != DataFieldStyle.SIMPLE) {
			throw new IllegalArgumentException("Champ de données simples attendu !"); 
		}
		
		super.update(code, name, type, style, description);
	}

	@Override
	public String defaultValue() throws IOException {
		return simpleRecord.valueOf(SimpleDataField::defaultValue);
	}

	@Override
	public void defaultValue(String value) throws IOException {
		
		String valueCleaned = StringUtils.trim(value);
		
		if(StringUtils.isNotBlank(valueCleaned) && !new DataFieldValueImpl(this, value).isValid()) {
			throw new IllegalArgumentException(String.format("La valeur par défaut <%s> ne respecte pas le type du champ !", valueCleaned));
		}		
		
		simpleRecord.entryOf(SimpleDataField::defaultValue, valueCleaned)
		      .update();		
	}

	@Override
	public boolean takeLastValue() throws IOException {
		return simpleRecord.valueOf(SimpleDataField::takeLastValue);
	}

	@Override
	public void takeLastValue(boolean takeLastValue) throws IOException {
		simpleRecord.entryOf(SimpleDataField::takeLastValue, takeLastValue)
		      .update(); 
	}

	@Override
	public boolean mustEditOnce() throws IOException {
		return simpleRecord.valueOf(SimpleDataField::mustEditOnce);
	}

	@Override
	public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
		simpleRecord.entryOf(SimpleDataField::mustEditOnce, mustEditOnce)
		      .update();
	}

	@Override
	public boolean isReadOnly() throws IOException {
		return simpleRecord.valueOf(SimpleDataField::isReadOnly);
	}

	@Override
	public void makeReadOnly(boolean readOnly) throws IOException {
		simpleRecord.entryOf(SimpleDataField::isReadOnly, readOnly)
		      .update();
	}

	@Override
	public void update(String code, String name, DataFieldType type, String description) throws IOException {
		super.update(code, name, type, DataFieldStyle.SIMPLE, description); 
	}
}
