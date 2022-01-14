package com.minlessika.sdk.datasource;

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

public class BaseStatementQueryable extends BaseStatement {

	public BaseStatementQueryable(Base base, String statement, List<Object> parameters) {
		super(base, statement, parameters);
	}

	@Override
	public List<ResultStatement> execute() throws IOException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ResultStatement> results = new ArrayList<>();
		
		try {		
			pstmt = prepareStatement(Statement.NO_GENERATED_KEYS);	
            rs = pstmt.executeQuery();    
            while(rs.next()){
            	ResultSetMetaData metadata = rs.getMetaData();
            	
            	Map<String, Object> data = new HashMap<>();
            	for (int i = 1; i <= metadata.getColumnCount(); i++) {
            		data.put(metadata.getColumnName(i), rs.getObject(i));
				}
            	
            	results.add(new ResultStatementImpl(data));
            }                                             
        } catch(SQLException ex) {
        	throw new IOException(ex);
        }
		finally {
        	close(rs);
        	close(pstmt);        	
        	close(base);
        }
		
		return results;	
	}

}
