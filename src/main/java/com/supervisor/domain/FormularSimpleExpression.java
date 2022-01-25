package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
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
