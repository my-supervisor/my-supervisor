package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_activity_template_subscription",
		label="Abonnement à un modèle d'activité"
)
public interface ActivityTemplateSubscription extends Recordable {
	
	@Field(
		label="Visiteur", 
		rel=Relation.MANY2ONE
	)
	User user() throws IOException;
	
	@Field(
		label="Modèle d'activité", 
		rel=Relation.MANY2ONE
	)
	ActivityTemplatePublished template() throws IOException;
}
