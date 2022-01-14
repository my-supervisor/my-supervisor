package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.AllProfiles;
import com.minlessika.membership.domain.Profile;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

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
