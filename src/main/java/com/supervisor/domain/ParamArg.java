package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=DataFieldArg.class
)
public interface ParamArg extends DataFieldArg {
	
	@Field(
		label="Champ", 
		rel= Relation.MANY2ONE,
		ignore=true
	)
	ParamDataField field() throws IOException;
}
