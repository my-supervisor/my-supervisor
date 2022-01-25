package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public final class PgInsertStatement extends PgStatementUpdatable {

	public PgInsertStatement(final Base base, final String tableName, final Map<String, Object> fieldValues, final UUID guid) {
		super(base, statement(tableName, fieldValues), parameters(fieldValues, guid, base.currentUserId()));
	}
	
	private static List<Object> parameters(final Map<String, Object> fieldValues, final UUID guid, final Long userId){
		List<Object> parameters = new ArrayList<>();
		parameters.addAll(fieldValues.values());
		parameters.add(userId);
		
		if(!fieldValues.containsKey("owner_id"))
			parameters.add(userId);
		
		parameters.add(guid);
		parameters.add(userId);
		
		return parameters;
	}
	
	private static String statement(final String tableName, final Map<String, Object> fieldValues) {
		Set<Entry<String, Object>> fvs = fieldValues.entrySet();
		
		String fields = "";
		String values = "";
		
		for (Entry<String, Object> entry : fvs) {
			if (fields == "")
				fields = entry.getKey();
			else
				fields += ", " + entry.getKey();
			
			if (values == "")
				values = "?";
			else
				values += ", ?";
		}
		
		if(!fieldValues.containsKey("owner_id"))			
			return String.format("INSERT INTO %s (%s, creation_date, last_modification_date, last_modifier_id, owner_id, guid, creator_id) VALUES (%s, now(), now(), ?, ?, ?, ?)", tableName, fields, values);		
		else
			return String.format("INSERT INTO %s (%s, creation_date, last_modification_date, last_modifier_id, guid, creator_id) VALUES (%s, now(), now(), ?, ?, ?)", tableName, fields, values);
	}

}
