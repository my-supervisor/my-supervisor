package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.ParamDataField;

public final class PxActivityParam extends ParamDataFieldWrap implements ActivityParam {

	private final Record<ActivityParam> record;
	
	public PxActivityParam(final Record<ActivityParam> record) throws IOException {
		super(fieldOf(record));
		
		this.record = record;
	}

	private static ParamDataField fieldOf(final Record<ActivityParam> record) throws IOException {
		return new PxParamDataField(record.listOf(DataField.class).get(record.id()));
	}
	
	@Override
	public Activity activity() throws IOException {
		Record<Activity> rec = record.of(ActivityParam::activity);
		return new PxActivity(rec);
	}
	
	@Override
	public String value() throws IOException {
		return record.valueOf(ActivityParam::value);
	}
	
	@Override
	public void update(String value) throws IOException {		
		
		record.isRequired(ActivityParam::value, value);
		
		new DataFieldValueImpl(this, value).validate();
		
		record.entryOf(ActivityParam::value, new DataFieldValueImpl(this, value).cleaned())
		      .update();
	}
}
