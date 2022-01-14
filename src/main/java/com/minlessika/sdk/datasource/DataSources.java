package com.minlessika.sdk.datasource;

import javax.sql.DataSource;

public interface DataSources {
	DataSource create();
	DataSource create(String name);
}
