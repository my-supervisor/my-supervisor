package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	comodel=DataFieldArg.class
)
public interface ParamArg extends DataFieldArg {
	
	@Field(
		label="Champ", 
		rel=Relation.MANY2ONE,
		ignore=true
	)
	ParamDataField field() throws IOException;
}
