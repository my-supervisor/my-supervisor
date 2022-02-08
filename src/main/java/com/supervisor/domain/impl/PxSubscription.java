package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Subscription;

public final class PxSubscription extends DomainRecordables<SharedResource, Subscription> implements Subscription {
	
	public PxSubscription(final RecordSet<SharedResource> source) throws IOException {
		super(PxSharedResource.class, source);
		
		this.source = source.where(SharedResource::subscriber, base().currentUserId());
	}

	@Override
	public void unsubscribe(SharedResource resource) throws IOException {		
		new UserOf(this).profile().validateAccessibility("ONE_UNSUBSCRIBE_DATA_SHEET_MODEL");
		remove(resource);
	}

	@Override
	public Optional<SharedResource> resource(UUID resourceId, ResourceType type) throws IOException {
		Subscription resourceToSearch =  where(SharedResource::resourceId, resourceId)
                				        .where(SharedResource::type, type);

		if(resourceToSearch.isEmpty())
			return Optional.empty();
		else
			return Optional.of(resourceToSearch.first());
	}
	
}
