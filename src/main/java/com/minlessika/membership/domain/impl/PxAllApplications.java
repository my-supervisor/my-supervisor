package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.AllApplications;
import com.minlessika.membership.domain.Application;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAllApplications extends DomainRecordables<Application, AllApplications> implements AllApplications {

	public PxAllApplications(final Base base) throws IOException {
		this(base.select(Application.class));
	}

	public PxAllApplications(final RecordSet<Application> source) throws IOException {
		super(PxApplication.class, source);
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
		final String module = base().appInfo().currentModule();
		return get(module);
	}

}
