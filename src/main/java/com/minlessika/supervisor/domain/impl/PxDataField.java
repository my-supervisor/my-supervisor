package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.indicator.impl.TypedDataModel;

public abstract class PxDataField extends DomainRecordable<DataField> implements DataField {

	private static final Logger logger = new MLogger(PxDataField.class);
	
	public PxDataField(Record<DataField> record) throws IOException {
		super(record);
		
		checkStyle(record);
	}

	protected abstract void checkStyle(Record<DataField> record) throws IOException;
	
	@Override
	public DataModel model() throws IOException {
		return TypedDataModel.convert(record.of(DataField::model));
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(DataField::code);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(DataField::name);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(DataField::description);
	}

	@Override
	public DataFieldType type() throws IOException {
		return record.valueOf(DataField::type);
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {

		record.isRequired(DataField::code, code); 		
		record.isRequired(DataField::name, name);
		
		record.mustCheckThisCondition(
			type != DataFieldType.NONE, 
			"Vous devez donner un type au champ !"
		);
		
		record.mustBeUnique(DataField::code, code.toUpperCase(), DataField::model);
		
		record.entryOf(DataField::name, name)
			  .entryOf(DataField::code, code.toUpperCase())
			  .entryOf(DataField::type, type)
			  .entryOf(DataField::description, description)			  
			  .update();
	}

	@Override
	public DataFieldStyle style() throws IOException {
		return record.valueOf(DataField::style);
	}

	@Override
	public String toString() {
		try {
			return String.format("%s - %s", code(), name());
				
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}
