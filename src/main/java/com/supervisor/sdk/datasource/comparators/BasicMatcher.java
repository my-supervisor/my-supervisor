package com.supervisor.sdk.datasource.comparators;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicMatcher implements Matcher {

	protected final List<Object> operands;
	protected final Object operand;
	protected final Comparator operator;
	
	public BasicMatcher(final Comparator operator, final List<Object> operands) {
		
		this.operator = operator;
		
		if(operands.isEmpty()){
			this.operand = null;
		}else {
			this.operand = cleanOperand(operands.get(0));		
		}	
		
		this.operands = new ArrayList<>();
		
		for (Object object : operands) {
			this.operands.add(cleanOperand(object));
		}
	}
	
	private Object cleanOperand(Object operand) {
		Object cleanOperand;
		
		if(StringUtils.contains(String.format("%s", operand), "%"))
			return operand;
		
		switch (operator) {
			case CONTAINS:
			case NOT_CONTAINS:
				cleanOperand =  "%" + String.format("%s", operand) + "%";
				break;
			case STARTS_WITH:
			case NOT_STARTS_WITH:
				cleanOperand = String.format("%s", operand) + "%";
				break;
			case ENDS_WITH:
			case NOT_ENDS_WITH:
				cleanOperand = "%" + String.format("%s", operand);
				break;
			default:
				cleanOperand = operand;
		}			
		
		return cleanOperand;
	}
	
	public BasicMatcher(final Comparator operator) {
		this(operator, Arrays.asList());
	}
	
	public BasicMatcher(final Comparator operator, final Object operand) {
		this(operator, Arrays.asList(operand));
	}
	
	@Override
	public Comparator operator() {
		return operator;
	}

	@Override
	public Object operand() {
		return operand;
	}

	@Override
	public List<Object> operands() {
		return operands;
	}

	@Override
	public Matcher withOperands(List<Object> operands) {
		return new BasicMatcher(operator, operands);
	}

	@Override
	public Matcher withOperand(Object operand) {
		return new BasicMatcher(operator, operand);
	}
}
