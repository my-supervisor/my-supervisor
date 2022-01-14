package com.minlessika.sdk.pgsql.statement;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseStatement;
import com.minlessika.sdk.datasource.ResultStatement;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public final class PgCreateDatabase extends BaseStatement {

	public PgCreateDatabase(Base base, String name) {
		super(base, String.format("CREATE DATABASE %s;", name), Arrays.asList());
	}

	@Override
	public List<ResultStatement> execute() throws IOException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = prepareStatement(Statement.NO_GENERATED_KEYS);
			pstmt.execute();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
        	close(pstmt); 
        	close(base);
        }
		
		return Arrays.asList();
	}

}
