package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_expression_value_arg", 
	label="Argument expression d'une expression",
	comodel=ExpressionArg.class
)
public interface ExpressionValueArg extends ExpressionArg {
	
	@Field(
		label="Expression cible", 
		rel=Relation.MANY2ONE
	)
	FormularExpression targetExpr() throws IOException;
}
