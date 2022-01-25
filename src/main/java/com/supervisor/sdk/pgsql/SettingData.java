package com.supervisor.sdk.pgsql;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.BaseScheme;

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
