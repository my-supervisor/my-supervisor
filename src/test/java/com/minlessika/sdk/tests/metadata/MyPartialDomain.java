package com.minlessika.sdk.tests.metadata;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Recordable;
import com.minlessika.sdk.metadata.Relation;

@Recordable(
	comodel=MyDomain.class		
)
public interface MyPartialDomain {
		
	@Field(name="name", label="Domain name")
	String name();
	
	@Field(name="full_name")
	String fullName();
	
	@Field
	String myVersion();
	
	@Field
	String description();
	
	boolean isNotAField();
	
	@Field(rel=Relation.MANY2ONE)
	MyModule myModule();
}
