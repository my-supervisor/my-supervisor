package com.minlessika.sdk.datasource.conditions.pgsql;

import com.minlessika.sdk.datasource.comparators.Matcher;
import com.minlessika.sdk.datasource.comparators.pgsql.PgSmartMatcher;
import com.minlessika.sdk.datasource.conditions.Condition;
import com.minlessika.sdk.datasource.conditions.Filter;
import com.minlessika.sdk.datasource.conditions.GroupedCondition;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PgFilter<A1> implements Filter<A1> {

	protected final Class<A1> clazz;
	protected final ConditionOperator operator;
	protected final List<Condition> conditions;
	
	public PgFilter(final Class<A1> clazz) {
		this(clazz, ConditionOperator.AND);
	}
	
	public PgFilter(final Class<A1> clazz, final ConditionOperator operator, final List<Condition> conditions) {
		this.clazz = clazz;
		this.operator = operator;
		this.conditions = conditions;
	}
	
	public PgFilter(final Class<A1> clazz, final ConditionOperator operator) {
		this(clazz, operator, new ArrayList<>());
	}
	
	public PgFilter(final Class<A1> clazz, final ConditionOperator operator, final Condition condition) throws IOException {
		this(clazz, operator);
		
		this.conditions.add(condition);
	}
	
	@Override
	public Filter<A1> add(final MethodRefWithoutArg<A1> methodRef, final Matcher matcher) throws IOException {
		
		Condition newCondition = new PgSimpleCondition(
										clazz, 
										methodRef, 
										new PgSmartMatcher(clazz, methodRef, matcher)
								  );
		
		return add(newCondition);
	}
	
	@Override
	public Filter<A1> add(final FieldMetadata field, final Matcher matcher) throws IOException {
		
		Condition newCondition = new PgSimpleCondition(
										field, 
										new PgSmartMatcher(field, matcher)
								  );
		
		return add(newCondition);	
	}
	
	@Override
	public Filter<A1> add(final Condition condition) throws IOException {
				
		// vérifier que cette condition n'existe pas déjà
		boolean exists = conditions.stream()
				                   .anyMatch(c -> c.equals(condition));
		if(!exists) {
			this.conditions.add(condition);
		}
		
		return this;
	}
	
	@Override
	public Filter<A1> append(Filter<A1> filter) throws IOException {
		this.conditions.add(filter.condition());
		return this;
	}
	
	private Condition join(Condition right, Condition left){
		
		Condition newCondition = Condition.EMPTY;
		
		switch (operator) {
			case AND:
				newCondition = new PgAnd(right, left);
				break;
			case OR:
				newCondition = new PgOr(right, left);
				break;
			default:
				break;
		}
		
		return newCondition;
	}

	@Override
	public Condition condition() throws IOException {
		Condition condition = Condition.EMPTY;
		
		if(conditions.size() == 1)
			condition = conditions.get(0);
		else if(conditions.size() > 1) {
			
			for (Condition left : conditions) {
				condition = join(condition, left);
			}
			
			condition = new GroupedCondition(condition); 
		}
		
		return condition;
	}

	@Override
	public Filter<A1> copy() {
		List<Condition> conditions = this.conditions.stream()
		         						 .collect(Collectors.toList());
		
		return new PgFilter<>(clazz, operator, conditions);
	}
}
