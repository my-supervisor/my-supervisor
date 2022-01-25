package com.supervisor.sdk.datasource;

import javax.sql.DataSource;

public interface DataSources {
	DataSource create();
	DataSource create(String name);
}
