package com.supervisor.sdk.datasource.comparators;

public class EndsWith extends BasicMatcher {
	
	public EndsWith(Object operand) {
		super(Comparator.ENDS_WITH, operand);
	}
}
