package com.minlessika.sdk.tests.metadata;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Recordable;

@Recordable
public interface MyModule {
	
	@Field(isKey=true)
	Long id();
	
	@Field
	String name();
}
