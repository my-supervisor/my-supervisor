package com.supervisor.domain;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Resources {
	List<Resource> items() throws IOException;
	Optional<Resource> resourceIfPresent(ResourceType type, Long id) throws IOException;
	Resource resource(ResourceType type, Long id) throws IOException;
}
