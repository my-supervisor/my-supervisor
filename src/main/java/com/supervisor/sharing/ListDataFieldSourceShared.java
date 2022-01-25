package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="supervisor_list_data_field_source_shared", 
	label="List data field source shared",
	comodel=ListDataFieldSource.class
)
public interface ListDataFieldSourceShared extends DataShared<ListDataFieldSource> {
	
	@Field(
		label="Modèle",
		rel= Relation.MANY2ONE
	)
	ListDataFieldSource template() throws IOException;
}
