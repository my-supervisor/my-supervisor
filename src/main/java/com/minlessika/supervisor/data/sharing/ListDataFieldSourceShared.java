package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.ListDataFieldSource;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_list_data_field_source_shared", 
	label="List data field source shared",
	comodel=ListDataFieldSource.class
)
public interface ListDataFieldSourceShared extends DataShared<ListDataFieldSource> {
	
	@Field(
		label="Modèle",
		rel=Relation.MANY2ONE
	)
	ListDataFieldSource template() throws IOException;
}
