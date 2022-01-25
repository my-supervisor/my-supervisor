package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface History extends DomainSet<Event, History> {
	Event add(String identifier, EventLevel level, String information, String details) throws IOException;
	History of(String identifier) throws IOException;
}
