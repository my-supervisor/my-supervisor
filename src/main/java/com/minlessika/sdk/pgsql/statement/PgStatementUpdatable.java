package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseStatement;
import com.minlessika.sdk.datasource.ResultStatement;
import com.minlessika.sdk.datasource.ResultStatementImpl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PgStatementUpdatable extends BaseStatement {

	public PgStatementUpdatable(final Base base, final String statement, final List<Object> parameters) {
		super(base, statement, parameters);
	}
	
	@Override
	public List<ResultStatement> execute() throws IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ResultStatement> results = new ArrayList<>();
		
		try {
			pstmt = prepareStatement(Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while(rs.next()){
            	ResultSetMetaData metadata = rs.getMetaData();
            	
            	Map<String, Object> data = new HashMap<>();
            	data.put(metadata.getColumnName(1), rs.getObject(1));
            	
            	results.add(new ResultStatementImpl(data));
            }
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			close(rs);
        	close(pstmt); 
        	close(base);
        }
		
		return results;
	}

}
