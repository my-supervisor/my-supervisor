package com.supervisor.sdk.datasource;

import com.supervisor.sdk.metadata.FieldMetadata;
import com.supervisor.sdk.metadata.MethodReferenceUtils;
import java.io.IOException;
import java.util.UUID;

public interface Record<A1 extends Recordable> extends Recordable {	
	
	String name() throws IOException;
	
	Base base();
	
	<T> T valueOf(String field) throws IOException;	
	<T> T valueOf(FieldMetadata field) throws IOException;
	<T> T valueOf(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException;
	
	Record<A1> entryOf(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException;
	void update() throws IOException;
	
	<T1 extends Recordable> RecordSet<T1> listOf(Class<T1> clazz) throws IOException;
	RecordSet<A1> toList() throws IOException;
	<T1 extends Recordable> Record<T1> of(Class<T1> clazz, UUID id);
	<T extends Recordable> Record<T> of(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException;
	
	void mustBeUnique(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object newValue) throws IOException;
	void mustBeUnique(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object newValue, MethodReferenceUtils.MethodRefWithoutArg<A1> fieldRef) throws IOException;
	void isRequired(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, String value) throws IOException;
	void mustCheckThisCondition(boolean condition, String msg) throws IOException;
}
