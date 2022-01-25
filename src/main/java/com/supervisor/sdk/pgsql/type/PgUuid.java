package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

import java.util.UUID;

public final class PgUuid extends BasicType {

	public PgUuid() {
		super("uuid", UUID.class);
	}
	
	@Override
	public Object cast(String value) {
		return UUID.fromString(value);
	}

}
