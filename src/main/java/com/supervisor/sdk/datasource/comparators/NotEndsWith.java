package com.supervisor.sdk.datasource.comparators;

public class NotEndsWith extends BasicMatcher {
	
	public NotEndsWith(Object operand) {
		super(Comparator.NOT_ENDS_WITH, operand);
	}
}
