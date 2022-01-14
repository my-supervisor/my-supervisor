package com.minlessika.sdk.datasource.conditions;

import java.io.IOException;
import java.util.List;

public class ConditionWrap extends BaseCondition {

	private final Condition origin;
	
	public ConditionWrap(final Condition origin) {
		this.origin = origin;
	}
	
	@Override
	public String toScritpt() throws IOException {
		return origin.toScritpt();
	}

	@Override
	public List<Object> parameters() throws IOException {
		return origin.parameters();
	}

}
