package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.metadata.FieldMetadata;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public final class PgCreateColumnStatement extends PgUpdateSchemaStatement {

	public PgCreateColumnStatement(Base base, final FieldMetadata column) {
		this(base, column, null);
	}
	
	public PgCreateColumnStatement(Base base, final FieldMetadata column, final Object defaultValue) {
		super(
				base, 
				script(column, column.entityName(), defaultValue), 
				defaultValue == null ? Arrays.asList() : Arrays.asList(defaultValue)
		);
	}

	private static String script(FieldMetadata column, String entityName, Object defaultValue) {
		
		String script = String.format("ALTER TABLE %s ADD COLUMN %s %s", entityName, column.name(), column.type().name());
		
		if(defaultValue != null) {
			script = String.format(
							"%s; \r\n "
							+ "UPDATE %s SET %s = ?", script, column.entityName(), column.name()
					);
		}
		
		if(column.definitionScript().contains("NOT NULL"))
			script = String.format(
					"%s; \r\n "
					+ "ALTER TABLE %s ALTER COLUMN %s SET NOT NULL", script, entityName, column.name());
		
		if(StringUtils.isNotBlank(column.constraintScript())) {
			script = String.format(
					"%s; \r\n"
					+ "ALTER TABLE %s ADD %s", script, entityName, column.constraintScript()
			);
		}		
		
		script = String.format(
				"%s; \r\n"
				+ "%s", 
				script,
				StringUtils.replace(column.commentScript(), column.entityName(), entityName)
		);
		
		return script;
	}
}
