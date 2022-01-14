package com.minlessika.sdk.datasource.comparators;

import java.io.IOException;
import java.util.List;

public class MatcherWrap implements Matcher {

	private final Matcher origin;
	
	public <A1> MatcherWrap(Matcher origin) throws IOException {
		this.origin = origin;
	}
	
	@Override
	public Comparator operator() {
		return origin.operator();
	}

	@Override
	public Object operand() {
		return origin.operand();
	}

	@Override
	public List<Object> operands() {
		
		
		return origin.operands();
	}

	@Override
	public Matcher withOperands(List<Object> operands) {
		return origin.withOperands(operands);
	}

	@Override
	public Matcher withOperand(Object operand) {
		return origin.withOperand(operand);
	}
}
