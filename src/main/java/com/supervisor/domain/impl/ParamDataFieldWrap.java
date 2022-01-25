package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.ParamDataField;

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
