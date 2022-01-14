package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.AggregatedModel;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_aggregated_model_shared", 
	label="Aggregated model shared",
	comodel=AggregatedModel.class
)
public interface AggregatedModelShared extends DataShared<AggregatedModel> {
	
	@Field(
		label="Modèle",
		rel=Relation.MANY2ONE
	)
	AggregatedModel template() throws IOException;
}
