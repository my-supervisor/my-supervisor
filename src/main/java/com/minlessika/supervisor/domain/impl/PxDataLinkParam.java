package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataLinkParam;
import com.minlessika.supervisor.domain.ParamDataField;

public final class PxDataLinkParam extends ParamDataFieldWrap implements DataLinkParam {
	
	private final Record<DataLinkParam> record;
	
	public PxDataLinkParam(final Record<DataLinkParam> record) throws IOException {
		super(fieldOf(record));
		
		this.record = record;
	}

	private static ParamDataField fieldOf(final Record<DataLinkParam> record) throws IOException {
		return new PxParamDataField(record.listOf(DataField.class).get(record.id()));
	}
	
	@Override
	public DataLink link() throws IOException {
		Record<DataLink> rec = record.of(DataLinkParam::link);
		return new PxDataLink(rec);
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(DataLinkParam::value);
	}

	@Override
	public void update(String value) throws IOException {		
		
		record.isRequired(DataLinkParam::value, value);
		
		new DataFieldValueImpl(this, value).validate();
		
		record.entryOf(DataLinkParam::value, new DataFieldValueImpl(this, value).cleaned())
		      .update();
	}
}
