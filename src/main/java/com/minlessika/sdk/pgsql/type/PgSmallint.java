package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.util.Arrays;

public class PgSmallint extends BasicType {
	
	public PgSmallint() {
		super("smallint", Short.class, Arrays.asList(short.class));
	}

	@Override
	public Object cast(String value) {
		return Short.parseShort(value);
	}
}
