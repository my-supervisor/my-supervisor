package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
		name="supervisor_activity_template_view",
		label="Vue d'un modèle d'activité"
)
public interface ActivityTemplateView extends com.supervisor.sdk.datasource.Recordable {

	@Field(
		label="Visiteur", 
		rel= Relation.MANY2ONE
	)
	User user() throws IOException;
	
	@Field(
		label="Modèle d'activité", 
		rel=Relation.MANY2ONE
	)
	ActivityTemplatePublished template() throws IOException;
}
