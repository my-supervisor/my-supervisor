package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="supervisor_expression_value_arg", 
	label="Argument expression d'une expression",
	comodel=ExpressionArg.class
)
public interface ExpressionValueArg extends ExpressionArg {
	
	@Field(
		label="Expression cible", 
		rel= Relation.MANY2ONE
	)
	FormularExpression targetExpr() throws IOException;
}
