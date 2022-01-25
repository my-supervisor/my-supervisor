package com.minlessika.sdk.tests.metadata;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;
import com.supervisor.sdk.metadata.Relation;

@Recordable(
		name="test_my_co_domain", 
		label="Mon co domaine",
		comodel=MyDomain.class		
)
public interface MyCoDomain {
		
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
