package com.supervisor.domain.impl;

import com.supervisor.domain.Access;
import com.supervisor.domain.AccessParams;
import com.supervisor.domain.Profile;
import com.supervisor.domain.ProfileAccess;
import com.supervisor.domain.ProfileAccessParam;
import com.supervisor.domain.ProfileAccessParams;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxProfileAccess extends DomainRecordable<ProfileAccess> implements ProfileAccess {

	public PxProfileAccess(final Record<ProfileAccess> source) throws IOException {
		super(source);
	}

	@Override
	public String code() throws IOException {
		return accessInherited().code();
	}

	@Override
	public String name() throws IOException {
		return accessInherited().name();
	}

	@Override
	public AccessParams parameters() throws IOException {
		return accessInherited().parameters();
	}

	@Override
	public void update(String code, String name) throws IOException {
		accessInherited().update(code, name);
	}

	@Override
	public Profile profile() throws IOException {
		Record<Profile> rec = record.of(ProfileAccess::profile);
		return new PxProfile(rec);
	}

	@Override
	public Access accessInherited() throws IOException {
		Record<Access> rec = record.of(ProfileAccess::accessInherited);
		return new PxAccess(rec);
	}

	@Override
	public ProfileAccessParams parameterValues() throws IOException {	
		return new PgProfileAccessParams(this);
	}
	
	@Override
	public int intParamValueOf(String code) throws IOException {
		return Integer.parseInt(paramValueOf(code));
	}

	@Override
	public String paramValueOf(String code) throws IOException {
		return parameterValues().where(ProfileAccessParam::code, code).first().value();
	}
}
