package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Persons extends DomainSet<Person, Persons> {
	Person add(final String name, final String email) throws IOException;
}
