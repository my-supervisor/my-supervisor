package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.metadata.FieldMetadata;

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
