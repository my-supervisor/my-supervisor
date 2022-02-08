package com.supervisor.sdk.datasource;

import com.supervisor.sdk.metadata.Field;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@com.supervisor.sdk.metadata.Recordable
public interface Recordable {
	
	@Field(order=0, isKey=true, forceInherit=true)
	UUID id();
	
	@Field(order=101, forceInherit=true)
	LocalDateTime creationDate() throws IOException;
	
	@Field(order=102, forceInherit=true)
	UUID creatorId() throws IOException;
	
	@Field(order=103, forceInherit=true)
	LocalDateTime lastModificationDate() throws IOException;
	
	@Field(order=104, forceInherit=true)
	UUID lastModifierId() throws IOException;
	
	@Field(order=105, forceInherit=true)
	UUID ownerId() throws IOException;
	
	@Field(order=106, forceInherit=true, isMandatory=false)
	String tag() throws IOException;
	
	Base base();
	<T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException;
	<T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException;
}
