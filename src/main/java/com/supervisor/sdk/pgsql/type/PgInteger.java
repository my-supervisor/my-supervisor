package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgInteger extends BasicType {
	
	public PgInteger() {
		super("integer", Integer.class, Arrays.asList(int.class));
	}

	@Override
	public Object cast(String value) {
		return Integer.parseInt(value);
	}
}
