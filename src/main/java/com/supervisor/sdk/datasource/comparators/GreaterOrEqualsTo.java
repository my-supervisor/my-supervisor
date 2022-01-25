package com.supervisor.sdk.datasource.comparators;

public class GreaterOrEqualsTo extends BasicMatcher {
	
	public GreaterOrEqualsTo(Object operand) {
		super(Comparator.GREATER_OR_EQUALS, operand);
	}
}
