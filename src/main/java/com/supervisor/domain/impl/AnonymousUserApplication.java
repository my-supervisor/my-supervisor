package com.supervisor.domain.impl;

import com.supervisor.domain.Application;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordableGenerated;

import java.io.IOException;

public final class AnonymousUserApplication extends RecordableGenerated implements Application {

	private final String module;
	
	public AnonymousUserApplication(final Base base) {
		this(base, base.appInfo().currentModule());
	}
	
	public AnonymousUserApplication(final Base base, final String module) {
		super(base);
		
		this.module = module;
	}

	@Override
	public User user() throws IOException {
		return new DmUser(base, 2L);
	}

	@Override
	public String module() throws IOException {
		return module;
	}
	
	@Override
	public Profile profile() throws IOException {
		return new PxProfiles(base, module).anonymous();
	}
	
	@Override
	public void changeProfile(Profile newProfile) throws IOException {
		throw new UnsupportedOperationException("AnonymousUserApplication#changeProfile");
	}

}
