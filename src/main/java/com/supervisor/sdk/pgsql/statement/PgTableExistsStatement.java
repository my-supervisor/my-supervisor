package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseStatementQueryable;

import java.io.IOException;
import java.util.Arrays;

public final class PgTableExistsStatement extends BaseStatementQueryable {

	public PgTableExistsStatement(final Base base, final String tableName) {
		super(base, statement(tableName), Arrays.asList());
	}
	
	private static String statement(final String tableName) {
		return String.format( "SELECT " + 
							  "COUNT(*) AS nb " + 
							  "FROM pg_catalog.pg_tables " + 
							  "WHERE tablename = '%s'", tableName);
	}
	
	public Boolean exists() throws IOException {
		return (Long)execute().get(0).data().get("nb") > 0;
	}
}
