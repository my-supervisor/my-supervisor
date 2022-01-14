package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.Optional;

import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.SharedResource;
import com.minlessika.supervisor.domain.ResourceType;
import com.minlessika.supervisor.domain.Subscription;

public final class PxSubscription extends DomainRecordables<SharedResource, Subscription> implements Subscription {
	
	public PxSubscription(final RecordSet<SharedResource> source) throws IOException {
		super(PxSharedResource.class, source);
		
		this.source = source.where(SharedResource::subscriber, base().currentUserId());
	}

	@Override
	public void unsubscribe(SharedResource resource) throws IOException {		
		new UserOf(this).currentProfile().validateAccessibility("ONE_UNSUBSCRIBE_DATA_SHEET_MODEL");
		remove(resource);
	}

	@Override
	public Optional<SharedResource> resource(Long resourceId, ResourceType type) throws IOException {
		Subscription resourceToSearch =  where(SharedResource::resourceId, resourceId)
                				        .where(SharedResource::type, type);

		if(resourceToSearch.isEmpty())
			return Optional.empty();
		else
			return Optional.of(resourceToSearch.first());
	}
	
}
