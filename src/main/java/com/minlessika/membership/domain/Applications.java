package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Applications extends DomainSet<Application, Applications> {
	Application add(String module) throws IOException;
	boolean has(String module) throws IOException;
	Application get(String module) throws IOException;
	Application current() throws IOException;
}
