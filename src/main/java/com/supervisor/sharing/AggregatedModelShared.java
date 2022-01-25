package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="supervisor_aggregated_model_shared", 
	label="Aggregated model shared",
	comodel=AggregatedModel.class
)
public interface AggregatedModelShared extends DataShared<AggregatedModel> {
	
	@Field(
		label="Modèle",
		rel= Relation.MANY2ONE
	)
	AggregatedModel template() throws IOException;
}
