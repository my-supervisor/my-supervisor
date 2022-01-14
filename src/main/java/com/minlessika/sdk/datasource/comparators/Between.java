package com.minlessika.sdk.datasource.comparators;

import java.util.Arrays;

public class Between extends BasicMatcher {
	
	public Between(Object operand1, Object operand2) {
		super(Comparator.BETWEEN, Arrays.asList(operand1, operand2));
	}
}
