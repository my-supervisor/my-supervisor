package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Plans extends DomainSet<Plan, Plans> {
	Plan add(String reference, Profile profile, double price) throws IOException;
}
