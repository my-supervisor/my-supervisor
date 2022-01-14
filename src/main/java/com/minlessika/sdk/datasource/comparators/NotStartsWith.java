package com.minlessika.sdk.datasource.comparators;

public class NotStartsWith extends BasicMatcher {
	
	public NotStartsWith(Object operand) {
		super(Comparator.NOT_STARTS_WITH, operand);
	}
}
