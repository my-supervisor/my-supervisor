package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_simple_expression", 
	label="Expression simple d'une formule",
	comodel=FormularExpression.class
)
public interface FormularSimpleExpression extends FormularExpression {

	@Field(label="Fonction")
	FormularFunc func() throws IOException;	
	
	ExpressionArg arg(int position) throws IOException;
	
	void update(FormularFunc func) throws IOException;
}
