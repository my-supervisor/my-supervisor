package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface Activities extends DomainSet<Activity, Activities> {
	Activity add(String name, String description) throws IOException;
	Activities ownActivities() throws IOException;
}
