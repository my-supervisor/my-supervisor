package com.supervisor.sdk.datasource.comparators;

public final class Matchers {
	
	public static Matcher equalsTo(Object operand) {
		return new EqualsTo(operand);
	}
	
	public static Matcher notEqualsTo(Object operand) {
		return new NotEqualsTo(operand);
	}
	
	public static Matcher isPresent() {
		return new IsPresent();
	}
	
	public static Matcher isAbsent() {
		return new IsAbsent();
	}
	
	public static Matcher between(Object operand1, Object operand2) {
		return new Between(operand1, operand2);
	}
	
	public static Matcher notBetween(Object operand1, Object operand2) {
		return new NotBetween(operand1, operand2);
	}
	
	public static Matcher greaterOrEqualsTo(Object operand) {
		return new GreaterOrEqualsTo(operand);
	}
	
	public static Matcher greatThan(Object operand) {
		return new GreaterThan(operand);
	}
	
	public static Matcher lessOrEqualsTo(Object operand) {
		return new LessOrEqualsTo(operand);
	}
	
	public static Matcher lessThan(Object operand) {
		return new LessThan(operand);
	}
	
	public static Matcher contains(Object operand) {
		return new Contains(operand);
	}
	
	public static Matcher notContains(Object operand) {
		return new NotContains(operand);
	}
	
	public static Matcher startsWith(Object operand) {
		return new StartsWith(operand);
	}
	
	public static Matcher notStartsWith(Object operand) {
		return new NotStartsWith(operand);
	}
	
	public static Matcher endsWith(Object operand) {
		return new EndsWith(operand);
	}
	
	public static Matcher notEndsWith(Object operand) {
		return new NotEndsWith(operand);
	}
}
