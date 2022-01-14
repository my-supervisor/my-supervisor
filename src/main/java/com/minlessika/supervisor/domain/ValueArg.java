package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_value_expression_arg", 
	label="Argument valeur d'une expression",
	comodel=ExpressionArg.class
)
public interface ValueArg extends ExpressionArg {
	
	@Field(label="Valeur")
	String value() throws IOException;
	
	@Field(label="Type de valeur")
	DataFieldType valueType() throws IOException;
}
