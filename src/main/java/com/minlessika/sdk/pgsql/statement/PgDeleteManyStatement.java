package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public final class PgDeleteManyStatement extends PgStatementUpdatable {

	public PgDeleteManyStatement(final Base base, final String tableName, final String clause, final List<Object> parameters) {
		super(base, statement(tableName, clause), parameters);
	}
	
	private static String statement(final String tableName, final String clause) {		
		
		if(StringUtils.isBlank(clause))	
			return String.format("DELETE FROM %s", tableName);	
		else
			return String.format("DELETE FROM %s WHERE %s", tableName, clause);	
	}

}
