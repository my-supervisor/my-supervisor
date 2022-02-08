package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.ResourceType;

public final class PxSharedResource extends DomainRecordable<SharedResource> implements SharedResource {
	
	public PxSharedResource(final Record<SharedResource> source) throws IOException {
		super(source);
	}

	@Override
	public String name() throws IOException {
		return new ResourcesImpl(
					record.toList(), 
					new UserOf(this)
				).resource(type(), resourceId())
				                                 				   .name();
	}

	@Override
	public UUID resourceId() throws IOException {
		return record.valueOf(SharedResource::resourceId);
	}

	@Override
	public ResourceType type() throws IOException {
		return record.valueOf(SharedResource::type);
	}

	@Override
	public User subscriber() throws IOException {
		Record<User> rec = record.of(SharedResource::subscriber);
		return new DmUser(rec);
	}
}
