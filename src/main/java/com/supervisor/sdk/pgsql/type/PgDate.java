package com.supervisor.sdk.pgsql.type;

import com.supervisor.sdk.metadata.BasicType;

import java.time.LocalDate;

public final class PgDate extends BasicType {

	public PgDate() {
		super("date", LocalDate.class);
	}

	@Override
	public Object cast(String value) {
		return LocalDate.parse(value);
	}
}
