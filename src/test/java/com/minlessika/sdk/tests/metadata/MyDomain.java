package com.minlessika.sdk.tests.metadata;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Recordable;
import com.minlessika.sdk.metadata.Relation;

import java.util.List;

@Recordable(
		name="test_my_domain", 
		label="Mon domaine"
)
public interface MyDomain {
		
	@Field(order=1, isKey=true)
	Long id();
	
	@Field(name="name", label="Domain name")
	String name();
	
	@Field(name="full_name")
	String fullName();
	
	@Field(name="names")
	List<String> names();
	
	@Field
	String myVersion();
	
	@Field
	String description();
	
	boolean isNotAField();
	
	@Field(rel=Relation.MANY2ONE)
	MyModule myModule();
}
