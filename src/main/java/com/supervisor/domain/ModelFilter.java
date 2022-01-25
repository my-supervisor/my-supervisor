package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
		name="supervisor_model_filter", 
		label="Filtre d'un modèle"
)
public interface ModelFilter extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(
		label="Modèle agrégé", 
		rel= Relation.MANY2ONE
	)
	AggregatedModel model() throws IOException;
	
	ModelFilterConditions conditions() throws IOException;
}
