package com.supervisor.sdk.datasource.comparators;

public class StartsWith extends BasicMatcher {
	
	public StartsWith(Object operand) {
		super(Comparator.STARTS_WITH, operand);
	}
}
