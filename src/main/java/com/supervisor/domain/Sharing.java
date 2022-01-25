package com.supervisor.domain;

import java.io.IOException;
import java.util.Optional;

import com.supervisor.sdk.datasource.DomainSet;

public interface Sharing extends DomainSet<SharedResource, Sharing> {
	SharedResource share(Long resourceId, ResourceType type, User subscriber) throws IOException;
	SharedResource share(Long resourceId, ResourceType type, String email) throws IOException;
	void unsubscribe(SharedResource resource) throws IOException;
	Optional<SharedResource> resource(Long resourceId, ResourceType type, User subscriber) throws IOException;
}
