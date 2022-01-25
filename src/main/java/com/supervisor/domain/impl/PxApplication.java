package com.supervisor.domain.impl;

import com.supervisor.domain.Application;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxApplication extends DomainRecordable<Application> implements Application {

	public PxApplication(Record<Application> record) {
		super(record);
	}

	@Override
	public User user() throws IOException {
		return new DmUser(record.of(Application::user));
	}

	@Override
	public String module() throws IOException {
		return record.valueOf(Application::module);
	}
	
	@Override
	public Profile profile() throws IOException {
		Record<Profile> rec = record.of(Application::profile);
		return new PxProfile(rec);
	}
	
	@Override
	public void changeProfile(Profile newProfile) throws IOException {
		
		if(newProfile.equals(profile()))
			return;
		
		if(!newProfile.module().equals(module()))
			throw new IllegalArgumentException(String.format("New profile (%s) is not compatible with application (%s)!", newProfile.name(), module()));
		
		record.entryOf(Application::profile, newProfile.id())
		      .update();
	}

}
