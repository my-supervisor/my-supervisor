package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgBoolean extends BasicType {
	
	public PgBoolean() {
		super("boolean", Boolean.class, Arrays.asList(boolean.class));
	}

	@Override
	public Object cast(String value) {
		return Boolean.parseBoolean(value);
	}
}
