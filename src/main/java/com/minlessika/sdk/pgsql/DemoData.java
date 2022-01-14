package com.minlessika.sdk.pgsql;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseScheme;

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
