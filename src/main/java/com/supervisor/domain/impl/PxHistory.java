package com.supervisor.domain.impl;

import com.supervisor.domain.Event;
import com.supervisor.domain.EventLevel;
import com.supervisor.domain.History;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxHistory extends DomainRecordables<Event, History> implements History {

	private final User user;
	
	public PxHistory(final User user) throws IOException {
		this(user.base().select(Event.class), user);
	}
	
	public PxHistory(final RecordSet<Event> source, final User user) throws IOException {
		super(PxEvent.class, source);
		
		this.user = user;
		this.source = source.orderBy(Event::id, OrderDirection.DESC);
	}
	
	@Override
	protected History domainSetOf(final RecordSet<Event> source) throws IOException {
		return new PxHistory(source, user);
	}

	@Override
	public Event add(String identifier, EventLevel level, String information, String details) throws IOException {
		
		source.isRequired(Event::identifier, identifier);
		source.isRequired(Event::information, information);
		
		Record<Event> record = source.entryOf(Event::identifier, identifier)
								     .entryOf(Event::information, information)
								     .entryOf(Event::details, details)
								     .entryOf(Event::level, level)
								     .add();
		
		return domainOf(record);
	}

	@Override
	public History of(String identifier) throws IOException {
		return where(Event::identifier, identifier);
	}	
}
