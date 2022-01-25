package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.ParamDataField;

public final class PxParamDataField extends PxDataField implements ParamDataField {

	private final Record<ParamDataField> paramRecord;
	
	public PxParamDataField(final Record<DataField> record) throws IOException {
		super(record);
		
		this.paramRecord = record.listOf(ParamDataField.class).get(record.id());
	}
	
	@Override
	protected void checkStyle(Record<DataField> record) throws IOException {
		if(record.valueOf(DataField::style) != DataFieldStyle.PARAMETER)
			throw new IllegalArgumentException("Field isn't a parameter field !");
	}

	@Override
	public AggregatedModel model() throws IOException {
		return (AggregatedModel)super.model(); 
	}

	@Override
	public String value() throws IOException {
		return paramRecord.valueOf(ParamDataField::value);
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		throw new UnsupportedOperationException("ParamDataField::update");
	}
	
	@Override
	public void update(String code, String name, DataFieldType type) throws IOException {
		
		if(type == DataFieldType.TABLE) {
			throw new IllegalArgumentException("Champ de données simples attendu !"); 
		}
		
		super.update(code, name, type, DataFieldStyle.PARAMETER, StringUtils.EMPTY);
	}

	@Override
	public void update(String value) throws IOException {		
		
		paramRecord.isRequired(ParamDataField::value, value);
		
		new DataFieldValueImpl(this, value).validate();
		
		paramRecord.entryOf(ParamDataField::value, new DataFieldValueImpl(this, value).cleaned())
		      .update();
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return new NoDataFieldDependencies();
	}
}
