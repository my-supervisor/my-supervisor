package com.minlessika.sdk.datasource;

import com.minlessika.sdk.metadata.FieldMetadata;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public interface BaseScheme {
	<T> FieldMetadata fieldOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException;	
	<T> String scriptOf(Class<T> clazz) throws IOException;
	<T> String nameOf(Class<T> clazz) throws IOException;
	<T> String labelOf(Class<T> clazz) throws IOException;
	<T> String nameOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException;
	<T> String labelOf(Class<T> clazz, MethodRefWithoutArg<T> methodRef) throws IOException;
	<T> List<FieldMetadata> fieldsOf(Class<T> clazz) throws IOException;
	<T> List<FieldMetadata> ownFieldsOf(Class<T> clazz) throws IOException;
	<T> FieldMetadata fieldOf(Class<T> clazz, Method m) throws IOException;
}
