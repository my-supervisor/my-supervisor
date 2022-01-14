package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.ParamDataField;

public class ParamDataFieldWrap extends DataFieldWrap implements ParamDataField {

	protected final ParamDataField origin;
	
	public ParamDataFieldWrap(final ParamDataField origin) {
		super(origin);
		
		this.origin = origin;
	}

	@Override
	public AggregatedModel model() throws IOException {
		return origin.model();
	}

	@Override
	public String value() throws IOException {
		return origin.value();
	}

	@Override
	public void update(String code, String name, DataFieldType type) throws IOException {
		origin.update(code, name, type); 
	}

	@Override
	public void update(String value) throws IOException {
		origin.update(value);
	}

}
