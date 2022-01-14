package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_shared_resource", 
		label="Ressource partagée"
)
public interface SharedResource extends Recordable {
	
	String name() throws IOException;
	
	@Field(label="ID de la ressource")
	Long resourceId() throws IOException;
	
	@Field(label="Type")
	ResourceType type() throws IOException;
	
	@Field(
		label="Abonné", 
		rel=Relation.MANY2ONE
	)
	User subscriber() throws IOException;
}
