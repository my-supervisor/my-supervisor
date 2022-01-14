package com.minlessika.sdk.datasource.comparators;

public class Contains extends BasicMatcher {
	
	public Contains(Object operand) {
		super(Comparator.CONTAINS, operand);
	}
}
