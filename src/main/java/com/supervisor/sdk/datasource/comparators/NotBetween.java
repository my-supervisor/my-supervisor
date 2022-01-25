package com.supervisor.sdk.datasource.comparators;

import java.util.Arrays;

public class NotBetween extends BasicMatcher {
	
	public NotBetween(Object operand1, Object operand2) {
		super(Comparator.NOT_BETWEEN, Arrays.asList(operand1, operand2));
	}
}
