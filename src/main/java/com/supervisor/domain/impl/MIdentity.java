package com.supervisor.domain.impl;

import com.supervisor.domain.User;
import org.takes.facets.auth.Identity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MIdentity implements Identity {

	private final Identity origin;
	
	public MIdentity(final User user) throws IOException {
		this(user, new HashMap<>());
	}
	
	public MIdentity(final User user, final Map<String, String> properties) throws IOException {
		
		Map<String, String> props = new HashMap<>();
		props.put("id", user.id().toString());
		props.put("email", user.address().email());
		props.put("password", user.password());
		props.putAll(properties);
		
		this.origin = new Simple(
						String.format("urn:minlessika:%d", user.id()),
						props
					  );
	}
	
	@Override
	public String urn() {
		return origin.urn();
	}

	@Override
	public Map<String, String> properties() {
		return origin.properties();
	}

}
