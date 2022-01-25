package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;

import java.util.Arrays;

public final class PgDropTableStatement extends PgUpdateSchemaStatement {

	public PgDropTableStatement(Base base, final String entity) {
		super(
				base, 
				script(entity), 
				Arrays.asList()
		);
	}

	private static String script(String entity) {	
		return String.format("DROP TABLE IF EXISTS %s;", entity);
	}
}
