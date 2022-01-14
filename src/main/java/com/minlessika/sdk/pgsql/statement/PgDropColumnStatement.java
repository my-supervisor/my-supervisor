package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;

import java.util.Arrays;

public final class PgDropColumnStatement extends PgUpdateSchemaStatement {

	public PgDropColumnStatement(Base base, final String entity, final String column) {
		super(
				base, 
				script(column, entity), 
				Arrays.asList()
		);
	}

	private static String script(String column, String entity) {	
		return String.format("ALTER TABLE %s DROP COLUMN IF EXISTS %s;", entity, column);
	}
}
