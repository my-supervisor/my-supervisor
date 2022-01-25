package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;

public final class PgRenameTableStatement extends PgUpdateSchemaStatement {

	public PgRenameTableStatement(Base base, final String oldName, final String newName) {
		super(
				base, 
				script(oldName, newName)
		);
	}

	private static String script(String oldName, String newName) {
		return String.format("ALTER TABLE IF EXISTS %s RENAME TO %s", oldName, newName);		
	}
}
