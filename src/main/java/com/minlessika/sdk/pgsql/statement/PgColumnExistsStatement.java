package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseStatementQueryable;
import com.minlessika.sdk.metadata.FieldMetadata;

import java.io.IOException;
import java.util.Arrays;

public final class PgColumnExistsStatement extends BaseStatementQueryable {

	public PgColumnExistsStatement(final Base base, final FieldMetadata column) {
		super(base, statement(column.entityName(), column.name()), Arrays.asList());
	}
	
	private static String statement(final String tableName, final String columnName) {
		return String.format( "SELECT " + 
							  "COUNT(*) AS nb " + 
							  "FROM information_schema.columns " + 
							  "WHERE table_name='%s' and column_name='%s'", tableName, columnName);
	}
	
	public Boolean exists() throws IOException {
		return (Long)execute().get(0).data().get("nb") > 0;
	}
}
