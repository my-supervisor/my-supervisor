package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

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
