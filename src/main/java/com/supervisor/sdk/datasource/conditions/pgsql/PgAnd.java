package com.supervisor.sdk.datasource.conditions.pgsql;

import com.supervisor.sdk.datasource.conditions.BaseCondition;
import com.supervisor.sdk.datasource.conditions.Condition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PgAnd extends BaseCondition {

	private final Condition right;
	private final Condition left;
	
	public PgAnd(final Condition right, final Condition left) {
		this.right = right;
		this.left = left;
	}
	
	@Override
	public String toScritpt() throws IOException {
		if(right == Condition.EMPTY) {
			return left.toScritpt();
		}else if(left == Condition.EMPTY){
			return right.toScritpt();
		}else {		
			return String.format("%s AND %s", right.toScritpt(), left.toScritpt());
		}
		
	}

	@Override
	public List<Object> parameters() throws IOException {
		List<Object> parameters = new ArrayList<>();
		parameters.addAll(right.parameters());
		parameters.addAll(left.parameters());
		
		return parameters;
	}

}
