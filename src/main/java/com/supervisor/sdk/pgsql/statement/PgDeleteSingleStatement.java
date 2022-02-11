package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;

import java.util.Arrays;

public final class PgDeleteSingleStatement extends PgStatementUpdatable {

	public PgDeleteSingleStatement(final Base base, final String tableName, final Long id) {
		super(base, statement(tableName), Arrays.asList(id));
	}
	
	private static String statement(final String tableName) {		
		return String.format("DELETE FROM %s WHERE id=?", tableName);
	}

}
