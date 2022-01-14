package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	comodel=DataFieldArg.class
)
public interface EditableDataFieldArg extends DataFieldArg {
	@Field(
		label="Champ", 
		rel=Relation.MANY2ONE,
		ignore=true
	)
	EditableDataField field() throws IOException;
}
