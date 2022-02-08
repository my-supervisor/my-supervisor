package com.supervisor.domain;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Resources {
	List<Resource> items() throws IOException;
	Optional<Resource> resourceIfPresent(ResourceType type, UUID id) throws IOException;
	Resource resource(ResourceType type, UUID id) throws IOException;
}
