package com.minlessika.supervisor.domain;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_case_expression", 
	label="Expression condition multiple d'une formule",
	comodel=FormularExpression.class
)
public interface FormularCaseExpression extends FormularExpression {
	
	WhenCases cases() throws IOException;	
	ExpressionArg defaultValue() throws IOException;
}
