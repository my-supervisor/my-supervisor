package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_data_link_param", 
		label="Paramètre d'une liaison de données",
		comodel=ParamDataField.class
)
public interface DataLinkParam extends ParamDataField {
	
	@Field(
		label="Liaison de données",
		rel=Relation.MANY2ONE
	)
	DataLink link() throws IOException;
	
	@Field(label="Value")
	String value() throws IOException;
}
