package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.SharedResource;
import com.minlessika.supervisor.domain.ResourceType;

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
	public Long resourceId() throws IOException {
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
