package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class PgSimpleUpdateStatement extends PgStatementUpdatable {

	public PgSimpleUpdateStatement(final Base base, final String tableName, final Map<String, Object> fieldValues, final Long id) {
		super(base, statement(tableName, fieldValues), parameters(fieldValues, id, base.currentUserId()));
	}
	
	private static List<Object> parameters(final Map<String, Object> fieldValues, final Long id, final Long userId){
		List<Object> parameters = new ArrayList<>();
		parameters.addAll(fieldValues.values());
		parameters.add(userId);
		parameters.add(id);
		
		return parameters;
	}
	
	private static String statement(final String tableName, final Map<String, Object> fieldValues) {
		Set<Entry<String, Object>> fvs = fieldValues.entrySet();
		
		String settings = "";
		
		for (Entry<String, Object> entry : fvs) {
			if (settings == "")
				settings += String.format("%s=?", entry.getKey());
			else
				settings += String.format(", %s=?", entry.getKey());
		}
		
		return String.format("UPDATE %s SET %s, last_modification_date=now(), last_modifier_id=? WHERE id=?", tableName, settings);		
	}

}
