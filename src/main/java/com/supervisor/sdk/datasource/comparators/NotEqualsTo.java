package com.supervisor.sdk.datasource.comparators;

public class NotEqualsTo extends BasicMatcher {
	public NotEqualsTo(Object operand) {
		super(Comparator.NOT_EQUALS, operand);
	}
}
