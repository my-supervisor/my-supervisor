package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgDouble extends BasicType {
	
	public PgDouble() {
		super("double precision", Double.class, Arrays.asList(double.class));
	}

	@Override
	public Object cast(String value) {
		return Double.parseDouble(value);
	}
}
