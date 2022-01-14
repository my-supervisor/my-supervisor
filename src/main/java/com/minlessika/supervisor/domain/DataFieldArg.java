package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_data_field_expression_arg", 
	label="Argument champ de données d'une expression",
	comodel=ExpressionArg.class
)
public interface DataFieldArg extends ExpressionArg {
	
	@Field(
		label="Champ", 
		rel=Relation.MANY2ONE
	)
	DataField field() throws IOException;
}
