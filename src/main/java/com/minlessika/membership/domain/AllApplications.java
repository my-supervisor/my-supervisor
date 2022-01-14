package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AllApplications extends DomainSet<Application, AllApplications> {
	boolean has(String module) throws IOException;
	Application get(String module) throws IOException;
	Application current() throws IOException;
}
