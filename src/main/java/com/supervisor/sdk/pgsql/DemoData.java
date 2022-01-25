package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseScheme;

import java.io.IOException;

public final class DemoData extends SetupDataBase {

	public DemoData(BaseScheme scheme, Class<?> clazz, Base base) {
		super(scheme, clazz, base);
	}

	@Override
	public String path() throws IOException {
		return "data/demo/"; 
	}

}
