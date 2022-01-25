package com.supervisor.sdk.datasource.comparators;

public final class EqualsTo extends BasicMatcher {

	public EqualsTo(Object operand) {
		super(Comparator.EQUALS, operand);
	}
}
