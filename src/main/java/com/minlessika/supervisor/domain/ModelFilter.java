package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_model_filter", 
		label="Filtre d'un modèle"
)
public interface ModelFilter extends Recordable {
	
	@Field(
		label="Modèle agrégé", 
		rel=Relation.MANY2ONE
	)
	AggregatedModel model() throws IOException;
	
	ModelFilterConditions conditions() throws IOException;
}
