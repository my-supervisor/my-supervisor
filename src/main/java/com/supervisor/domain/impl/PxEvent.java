package com.supervisor.domain.impl;

import com.supervisor.domain.Event;
import com.supervisor.domain.EventLevel;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxEvent extends DomainRecordable<Event> implements Event {

	public PxEvent(Record<Event> record) throws IOException {
		super(record);
	}

	@Override
	public String identifier() throws IOException {
		return record.valueOf(Event::identifier);
	}

	@Override
	public EventLevel level() throws IOException {
		return record.valueOf(Event::level);
	}

	@Override
	public String information() throws IOException {
		return record.valueOf(Event::information);
	}

	@Override
	public String details() throws IOException {
		return record.valueOf(Event::details);
	}
}
