package com.supervisor.domain.impl;

import com.supervisor.domain.AllProfiles;
import com.supervisor.domain.Profile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAllProfiles extends DomainRecordables<Profile, AllProfiles> implements AllProfiles {
	
	public PxAllProfiles(final Base base) throws IOException {
		this(base.select(Profile.class));
	}
	
	public PxAllProfiles(final RecordSet<Profile> source) {
		super(PxProfile.class, source);
	}
	
	@Override
	public void remove(Profile item) throws IOException {		
		new PxProfiles(base(), item.module()).remove(item);
	}

	@Override
	public Profile get(String code, String module) throws IOException {
		return this.where(Profile::code, code)
				   .where(Profile::module, module)
				   .first();
	}

	@Override
	public Profile getByTag(String tag, String module) throws IOException {
		return this.where(Profile::tag, tag)
				   .where(Profile::module, module)
				   .first();
	}
}
