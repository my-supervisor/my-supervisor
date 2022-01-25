package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
		name="supervisor_activity_param", 
		label="Paramètre d'une activité",
		comodel=ParamDataField.class
)
public interface ActivityParam extends ParamDataField {
	
	@Field(
			label="Activité",
			rel= Relation.MANY2ONE
	)
	Activity activity() throws IOException;
	
	@Field(label="Value")
	String value() throws IOException;
}
