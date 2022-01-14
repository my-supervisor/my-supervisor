package com.minlessika.sdk.datasource;

import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;

public interface Record<A1 extends Recordable> extends Recordable {	
	
	String name() throws IOException;
	
	Base base();
	
	<T> T valueOf(String field) throws IOException;	
	<T> T valueOf(FieldMetadata field) throws IOException;
	<T> T valueOf(MethodRefWithoutArg<A1> methodRef) throws IOException;	
	
	Record<A1> entryOf(MethodRefWithoutArg<A1> methodRef, Object value) throws IOException;
	void update() throws IOException;
	
	<T1 extends Recordable> RecordSet<T1> listOf(Class<T1> clazz) throws IOException;
	RecordSet<A1> toList() throws IOException;
	<T1 extends Recordable> Record<T1> of(Class<T1> clazz, Long id);
	<T extends Recordable> Record<T> of(MethodRefWithoutArg<A1> methodRef) throws IOException;
	
	void mustBeUnique(MethodRefWithoutArg<A1> methodRef, Object newValue) throws IOException;
	void mustBeUnique(MethodRefWithoutArg<A1> methodRef, Object newValue, MethodRefWithoutArg<A1> fieldRef) throws IOException;
	void isRequired(MethodRefWithoutArg<A1> methodRef, String value) throws IOException;
	void mustCheckThisCondition(boolean condition, String msg) throws IOException;
}
