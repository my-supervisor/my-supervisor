package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.datasource.ResultStatementImpl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public final class PgInsertStatement extends PgStatementUpdatable {

	private final Map<String, Object> fields;

	public PgInsertStatement(final Base base, final String tableName, final Map<String, Object> fields) {
		super(base, statement(tableName, fields), parameters(fields, base.currentUserId()));

		this.fields = fields;
	}
	
	private static List<Object> parameters(final Map<String, Object> fieldValues, final UUID userId){
		List<Object> parameters = new ArrayList<>();
		parameters.addAll(fieldValues.values());
		parameters.add(userId);
		
		if(!fieldValues.containsKey("owner_id"))
			parameters.add(userId);

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
			return String.format("INSERT INTO %s (%s, creation_date, last_modification_date, last_modifier_id, owner_id, creator_id) VALUES (%s, now(), now(), ?, ?, ?)", tableName, fields, values);
		else
			return String.format("INSERT INTO %s (%s, creation_date, last_modification_date, last_modifier_id, creator_id) VALUES (%s, now(), now(), ?, ?)", tableName, fields, values);
	}

	@Override
	public List<ResultStatement> execute() throws IOException {
		super.execute();
		final Map<String, Object> data = new HashMap<>();
		data.put("id", fields.get("id"));
		return Arrays.asList(new ResultStatementImpl(data));
	}

}
