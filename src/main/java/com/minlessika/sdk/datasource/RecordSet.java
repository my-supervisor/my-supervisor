package com.minlessika.sdk.datasource;

import com.minlessika.sdk.datasource.comparators.Matcher;
import com.minlessika.sdk.datasource.conditions.Condition;
import com.minlessika.sdk.datasource.conditions.Filter;
import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;

public interface RecordSet<A1 extends Recordable> extends EntitySet<Record<A1>> {		
	
	Base base();
	
	Filter<A1> filter();
	Filter<A1> orFilter();
	Filter<A1> andFilter();
	
	RecordSet<A1> entryOf(FieldMetadata field, Object value) throws IOException;
	RecordSet<A1> entryOf(MethodRefWithoutArg<A1> methodRef, Object value) throws IOException;
	
	<T1 extends Recordable> RecordSet<T1> of(Class<T1> clazz) throws IOException;
	<T1 extends Recordable> RecordSet<T1> of(Class<T1> clazz, String viewScript) throws IOException;
	
	RecordSet<A1> where(FieldMetadata field, Object value) throws IOException;
	RecordSet<A1> where(MethodRefWithoutArg<A1> methodRef, Object value) throws IOException;
	
	RecordSet<A1> where(FieldMetadata field, Matcher matcher) throws IOException;
	RecordSet<A1> where(MethodRefWithoutArg<A1> methodRef, Matcher matcher) throws IOException;
	
	RecordSet<A1> where(Condition left) throws IOException;
	RecordSet<A1> where(Filter<A1> filter) throws IOException; 
	
	RecordSet<A1> orderBy(FieldMetadata field, OrderDirection direction) throws IOException;
	RecordSet<A1> orderBy(MethodRefWithoutArg<A1> methodRef, OrderDirection direction) throws IOException;
	RecordSet<A1> orderBy(MethodRefWithoutArg<A1> methodRef1, MethodRefWithoutArg<A1> methodRef2, OrderDirection direction) throws IOException;
	RecordSet<A1> orderBy(FieldMetadata field) throws IOException;
	RecordSet<A1> orderBy(MethodRefWithoutArg<A1> methodRef) throws IOException;
	RecordSet<A1> orderBy(MethodRefWithoutArg<A1> methodRef1, MethodRefWithoutArg<A1> methodRef2) throws IOException;
	RecordSet<A1> orderBy(Ordering ordering) throws IOException;
	
	RecordSet<A1> limit(Long number) throws IOException;
	RecordSet<A1> start(Long position) throws IOException;
	
	Record<A1> add() throws IOException;
	Record<A1> addForUser(long uid) throws IOException;
	
	Object aggregate(String aggScript, String alias) throws IOException;
	
	void mustBeUnique(MethodRefWithoutArg<A1> methodRef, Object newValue) throws IOException;
	void mustBeUnique(MethodRefWithoutArg<A1> methodRef, Object newValue, MethodRefWithoutArg<A1> fieldRef, Object refValue) throws IOException;
	void isRequired(MethodRefWithoutArg<A1> methodRef, String value) throws IOException;
	void mustCheckThisCondition(boolean condition, String msg) throws IOException;
}
