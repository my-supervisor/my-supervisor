package com.supervisor.sdk.datasource;

import java.util.Map;

public final class ResultStatementImpl implements ResultStatement {

	final Map<String, Object> data;
	
	public ResultStatementImpl(final Map<String, Object> data) {
		this.data = data;
	}
	
	@Override
	public Map<String, Object> data() {
		return data;
	}

}
