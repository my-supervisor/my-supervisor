package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;

public final class PgRenameColumnStatement extends PgUpdateSchemaStatement {

	public PgRenameColumnStatement(Base base, final String tableName, final String oldName, final String newName) {
		super(
				base, 
				script(tableName, oldName, newName)
		);
	}

	private static String script(String tableName, String oldName, String newName) {
		return String.format("ALTER TABLE %s RENAME %s TO %s", tableName, oldName, newName);		
	}
}
