package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgLong extends BasicType {
	
	public PgLong() {
		super("bigint", Long.class, Arrays.asList(long.class));
	}

	@Override
	public Object cast(String value) {
		return Long.parseLong(value);
	}
}
