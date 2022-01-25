package com.supervisor.sdk.datasource.comparators;

public class LessThan extends BasicMatcher {

	public LessThan(Object operand) {
		super(Comparator.LESS_THAN, operand);
	}
}
