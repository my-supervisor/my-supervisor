package com.minlessika.sdk.datasource.comparators;

import java.util.List;

public interface Matcher {
	Comparator operator();
	Object operand();
	List<Object> operands();
	
	Matcher withOperands(List<Object> operands);
	Matcher withOperand(Object operand);
}
