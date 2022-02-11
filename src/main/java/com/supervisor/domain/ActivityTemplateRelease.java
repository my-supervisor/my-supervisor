package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="activity_template_release",
	label="Release d'un modèle d'activité"
)
public interface ActivityTemplateRelease extends com.supervisor.sdk.datasource.Recordable {

	@Field(
		label="Modèle", 
		rel= Relation.MANY2ONE
	)
	ActivityTemplate template() throws IOException;
	
	@Field(label="Version")
	String version() throws IOException;
	
	@Field(label="Notes")
	String notes() throws IOException;
	
	void update(String version, String notes) throws IOException;
	void build(Activity source) throws IOException;
	void applyTo(Activity target) throws IOException;
}
