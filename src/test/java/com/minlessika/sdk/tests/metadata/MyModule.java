package com.minlessika.sdk.tests.metadata;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable
public interface MyModule {
	
	@Field(isKey=true)
	Long id();
	
	@Field
	String name();
}
