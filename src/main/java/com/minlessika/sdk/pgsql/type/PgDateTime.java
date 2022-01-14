package com.minlessika.sdk.pgsql.type;

import com.minlessika.sdk.metadata.BasicType;

import java.time.LocalDateTime;

public final class PgDateTime extends BasicType {

	public PgDateTime() {
		super("timestamp without time zone", LocalDateTime.class);
	}
	
	@Override
	public Object cast(String value) {
		return LocalDateTime.parse(value);
	}

}
