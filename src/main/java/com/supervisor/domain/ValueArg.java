package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="value_expression_arg",
	label="Argument valeur d'une expression",
	comodel=ExpressionArg.class
)
public interface ValueArg extends ExpressionArg {
	
	@Field(label="Valeur")
	String value() throws IOException;
	
	@Field(label="Type de valeur")
	DataFieldType valueType() throws IOException;
}
