package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface History extends DomainSet<Event, History> {
	Event add(String identifier, EventLevel level, String information, String details) throws IOException;
	History of(String identifier) throws IOException;
}
