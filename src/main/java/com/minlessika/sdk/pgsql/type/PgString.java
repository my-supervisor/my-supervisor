package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

public final class PgString extends BasicType {

	public PgString() {
		super("character varying", String.class);
	}
	
	public PgString(final Class<?> clazz) {
		super("character varying", clazz);
	}

	@Override
	public Object cast(String value) {
		return value;
	}
}
