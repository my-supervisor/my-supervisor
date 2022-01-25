package com.supervisor.sdk.datasource.conditions;

import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;

public class FilterWrap<A1> implements Filter<A1> {

	private final Filter<A1> origin;
	
	public FilterWrap(final Filter<A1> origin) {
		this.origin = origin;
	}
	
	@Override
	public Filter<A1> add(Condition condition) throws IOException {
		return origin.add(condition);
	}

	@Override
	public Filter<A1> add(MethodRefWithoutArg<A1> methodRef, Matcher matcher) throws IOException {
		return origin.add(methodRef, matcher);
	}

	@Override
	public Filter<A1> add(FieldMetadata field, Matcher matcher) throws IOException {
		return origin.add(field, matcher);
	}

	@Override
	public Filter<A1> append(Filter<A1> filter) throws IOException {
		return origin.append(filter);
	}

	@Override
	public Condition condition() throws IOException {
		return origin.condition();
	}

	@Override
	public Filter<A1> copy() {
		return origin.copy();
	}
}
