package com.supervisor.domain;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
		name="shared_resource",
		label="Ressource partagée"
)
public interface SharedResource extends com.supervisor.sdk.datasource.Recordable {
	
	String name() throws IOException;
	
	@Field(label="ID de la ressource")
	UUID resourceId() throws IOException;
	
	@Field(label="Type")
	ResourceType type() throws IOException;
	
	@Field(
		label="Abonné", 
		rel= Relation.MANY2ONE
	)
	User subscriber() throws IOException;
}
