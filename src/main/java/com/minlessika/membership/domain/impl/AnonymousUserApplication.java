package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordableGenerated;

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
