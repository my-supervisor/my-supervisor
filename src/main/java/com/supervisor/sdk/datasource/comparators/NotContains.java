package com.supervisor.sdk.datasource.comparators;

public class NotContains extends BasicMatcher {
	
	public NotContains(Object operand) {
		super(Comparator.NOT_CONTAINS, operand);
	}
}
