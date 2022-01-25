package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.metadata.FieldMetadata;

public final class PgChangeColumnTypeStatement extends PgUpdateSchemaStatement {

	public PgChangeColumnTypeStatement(Base base, final FieldMetadata column) {
		super(
				base, 
				script(column)
		);
	}

	private static String script(FieldMetadata column) {
		
		return String.format("ALTER TABLE %s ALTER COLUMN %s TYPE %s USING %s::%s;", column.entityName(), column.name(), column.type().name(), column.name(), column.type().name());
	}
}
