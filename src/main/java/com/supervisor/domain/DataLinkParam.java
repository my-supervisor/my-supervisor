package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="data_link_param",
		label="Paramètre d'une liaison de données",
		comodel=ParamDataField.class
)
public interface DataLinkParam extends ParamDataField {
	
	@Field(
		label="Liaison de données",
		rel= Relation.MANY2ONE
	)
	DataLink link() throws IOException;
	
	@Field(label="Value")
	String value() throws IOException;
}
