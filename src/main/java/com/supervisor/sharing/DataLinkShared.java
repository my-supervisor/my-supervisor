package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.DataLink;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="supervisor_data_link_shared", 
	label="Data link shared",
	comodel=DataLink.class
)
public interface DataLinkShared extends DataShared<DataLink> {
	@Field(
		label="Modèle",
		rel= Relation.MANY2ONE
	)
	DataLink template() throws IOException;
}
