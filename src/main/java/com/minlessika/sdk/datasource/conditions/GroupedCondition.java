package com.minlessika.sdk.datasource.conditions;

import java.io.IOException;
import java.util.List;

public final class GroupedCondition implements Condition {

	private final Condition origin;
	
	public GroupedCondition(final Condition origin) {
		this.origin = origin;
	}
	
	@Override
	public String toScritpt() throws IOException {
		return String.format("(%s)", origin.toScritpt());
	}

	@Override
	public List<Object> parameters() throws IOException {
		return origin.parameters();
	}

}
