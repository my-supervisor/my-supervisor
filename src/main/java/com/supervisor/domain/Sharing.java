package com.supervisor.domain;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainSet;

public interface Sharing extends DomainSet<SharedResource, Sharing> {
	SharedResource share(UUID resourceId, ResourceType type, User subscriber) throws IOException;
	SharedResource share(UUID resourceId, ResourceType type, String email) throws IOException;
	void unsubscribe(SharedResource resource) throws IOException;
	Optional<SharedResource> resource(UUID resourceId, ResourceType type, User subscriber) throws IOException;
}
