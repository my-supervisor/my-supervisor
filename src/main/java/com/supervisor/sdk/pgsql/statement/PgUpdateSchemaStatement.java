package com.supervisor.sdk.pgsql.statement;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseStatement;
import com.supervisor.sdk.datasource.ResultStatement;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PgUpdateSchemaStatement extends BaseStatement {

	public PgUpdateSchemaStatement(final Base base, final String statement, final List<Object> parameters) {
		super(base, statement, parameters);
	}
	
	public PgUpdateSchemaStatement(final Base base, final String statement) {
		super(base, statement, new ArrayList<Object>());
	}
	
	@Override
	public List<ResultStatement> execute() throws IOException {
		PreparedStatement pstmt = null;
		
		try {			
			pstmt = prepareStatement(Statement.NO_GENERATED_KEYS);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
        	close(pstmt); 
        	close(base);
        }
		
		return new ArrayList<>();
	}

}
