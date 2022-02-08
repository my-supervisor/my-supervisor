package com.supervisor.domain;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainSet;

public interface Subscription extends DomainSet<SharedResource, Subscription> {
	void unsubscribe(SharedResource resource) throws IOException;
	Optional<SharedResource> resource(UUID resourceId, ResourceType type) throws IOException;
}
