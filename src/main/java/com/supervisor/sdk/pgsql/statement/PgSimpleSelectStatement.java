package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseStatementQueryable;
import com.supervisor.sdk.datasource.Table;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public final class PgSimpleSelectStatement extends BaseStatementQueryable {
	
	public PgSimpleSelectStatement(final Base base, final List<String> select, final Table table, final String clause, final List<Object> parameters) {
		this(base, select, table, clause, parameters, StringUtils.EMPTY, 0L, 0L);
	}
	
	public PgSimpleSelectStatement(final Base base, final List<String> select, final Table table, final String clause, final List<Object> parameters, final String ordering, final Long limit, final Long start) {
		super(base, statement(select, table, clause, ordering, limit, start), parameters);
	}
	
	private static String statement(final List<String> select, final Table table, final String clause, final String ordering, final Long limit, final Long start) {
		
		String queryBodyScript;
		
		if(StringUtils.isBlank(clause))
			queryBodyScript = String.format("%s", table.name());
		else
			queryBodyScript = String.format("%s WHERE %s", table.name(), clause);
		
		if(StringUtils.isNotBlank(ordering))
			queryBodyScript = String.format("%s %s", queryBodyScript, ordering);
		
		if(start > 0)
			queryBodyScript = String.format("%s offset %s", queryBodyScript, start - 1);
		
		if(limit > 0)
			queryBodyScript = String.format("%s limit %s", queryBodyScript, limit);
		
		String selectStatement = StringUtils.EMPTY;
		
		for (String field : select) {
			if (StringUtils.isBlank(selectStatement))
				selectStatement = field;
			else
				selectStatement = String.format("%s, %s", selectStatement, field);
		}
		
		return String.format("SELECT %s FROM %s", selectStatement, queryBodyScript);
	}
}
