package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

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
