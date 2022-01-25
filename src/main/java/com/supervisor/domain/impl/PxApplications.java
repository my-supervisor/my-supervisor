package com.supervisor.domain.impl;

import com.supervisor.domain.Application;
import com.supervisor.domain.Applications;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.mailing.InternetSafeMailing;

import java.io.IOException;

public final class PxApplications extends DomainRecordables<Application, Applications> implements Applications {

	private final User user;
	
	public PxApplications(final User user) throws IOException {
		this(user.listOf(Application.class), user);
	}

	public PxApplications(final RecordSet<Application> source, final User user) throws IOException {
		super(PxApplication.class, source);
		
		this.user = user;
		this.source = source.where(Application::user, user.id());
	}
	
	@Override
	protected Applications domainSetOf(final RecordSet<Application> source) throws IOException {
		return new PxApplications(source, user);
	}
	
	@Override
	public Application add(String module) throws IOException {
		
		if(user.id().equals(2L))
			throw new IllegalArgumentException("Anonymous user is not allowed to subscribe to a module !");
		
		if(has(module)) {
			return get(module);
		}
		
		final Profile userProfile = new PxProfiles(base(), module).simpleUser();
		Record<Application> record = source.entryOf(Application::user, user.id())
									       .entryOf(Application::module, module)
									       .entryOf(Application::profile, userProfile.id())
									       .add();
		
		new InternetSafeMailing().send(
				new UserAdmin(base()).address().email(), 
				"New module subscription",
				String.format("Subscription to module %s by %s", module, user.name())
		);
		
		return domainOf(record);
	}

	@Override
	public boolean has(String module) throws IOException {
		return where(Application::module, module).any();
	}

	@Override
	public Application get(String module) throws IOException {
		return where(Application::module, module).first();
	}

	@Override
	public Application current() throws IOException {
		final String module = user.base().appInfo().currentModule();
		return get(module);
	}

}
