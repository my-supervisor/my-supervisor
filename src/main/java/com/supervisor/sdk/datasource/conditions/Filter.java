package com.supervisor.sdk.datasource.conditions;

import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;

public interface Filter<A1> {
	
	Filter<A1> add(Condition condition) throws IOException;
	Filter<A1> add(MethodRefWithoutArg<A1> methodRef, Matcher matcher) throws IOException;
	Filter<A1> add(FieldMetadata field, Matcher matcher) throws IOException;
	
	Filter<A1> append(Filter<A1> filter) throws IOException;
	
	Condition condition() throws IOException;
	
	Filter<A1> copy();
}
