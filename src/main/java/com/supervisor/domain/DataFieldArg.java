package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="data_field_expression_arg",
	label="Argument champ de données d'une expression",
	comodel=ExpressionArg.class
)
public interface DataFieldArg extends ExpressionArg {
	
	@Field(
		label="Champ", 
		rel= Relation.MANY2ONE
	)
	DataField field() throws IOException;
}
