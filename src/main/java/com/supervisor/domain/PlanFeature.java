package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
	name="membership_plan_feature", 
	label="Fonctionnalité d'un mode tarifaire"
)
public interface PlanFeature extends Recordable {
	
	@Field(
		label="Mode de tarification",
		rel=Relation.MANY2ONE
	)
	Plan plan() throws IOException;
	
	@Field(label="Ordre", name="no")
	int order() throws IOException;
	
	@Field(label="Basic")
	boolean basic() throws IOException;
	
	@Field(label="Actif")
	boolean enabled() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Description", isMandatory=false)
	String description() throws IOException;
	
	void update(String name, boolean enabled, boolean basic) throws IOException;
	void organize(int order) throws IOException;
	void describe(String description) throws IOException;
}
