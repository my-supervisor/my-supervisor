package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgTinyint extends BasicType {
	
	public PgTinyint() {
		super("tinyint", Byte.class, Arrays.asList(byte.class));
	}

	@Override
	public Object cast(String value) {
		return Byte.parseByte(value);
	}
}
