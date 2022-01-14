package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_activity_template_release", 
	label="Release d'un modèle d'activité"
)
public interface ActivityTemplateRelease extends Recordable {

	@Field(
		label="Modèle", 
		rel=Relation.MANY2ONE
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
