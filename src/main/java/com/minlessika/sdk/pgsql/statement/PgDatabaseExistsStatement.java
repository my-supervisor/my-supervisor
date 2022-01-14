package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseStatementQueryable;

import java.io.IOException;
import java.util.Arrays;

public final class PgDatabaseExistsStatement extends BaseStatementQueryable {

	public PgDatabaseExistsStatement(final Base base, final String name) {
		super(base, statement(name), Arrays.asList());
	}
	
	private static String statement(final String tableName) {
		return String.format( "SELECT " + 
							  "COUNT(*) AS nb " + 
							  "FROM pg_database " + 
							  "WHERE datname = '%s'", tableName);
	}
	
	public Boolean exists() throws IOException {
		return (Long)execute().get(0).data().get("nb") > 0;
	}
}
