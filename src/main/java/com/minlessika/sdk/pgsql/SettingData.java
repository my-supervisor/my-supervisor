package com.minlessika.sdk.pgsql;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseScheme;

import java.io.IOException;

public final class SettingData extends SetupDataBase {

	public SettingData(BaseScheme scheme, Class<?> clazz, Base base) {
		super(scheme, clazz, base);
	}

	@Override
	public String path() throws IOException {
		return "data/settings/"; 
	}

}
