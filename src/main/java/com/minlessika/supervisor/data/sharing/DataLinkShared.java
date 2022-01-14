package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.DataLink;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_data_link_shared", 
	label="Data link shared",
	comodel=DataLink.class
)
public interface DataLinkShared extends DataShared<DataLink> {
	@Field(
		label="Modèle",
		rel=Relation.MANY2ONE
	)
	DataLink template() throws IOException;
}
