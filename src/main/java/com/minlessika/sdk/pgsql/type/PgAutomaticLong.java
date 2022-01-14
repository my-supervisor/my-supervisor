package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.util.Arrays;

public final class PgAutomaticLong extends BasicType {

	public PgAutomaticLong() {
		super("bigserial", Long.class, Arrays.asList(long.class));
	}
	
	@Override
	public Object cast(String value) {
		return Long.parseLong(value);
	}
}
