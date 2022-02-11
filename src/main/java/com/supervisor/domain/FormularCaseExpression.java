package com.supervisor.domain;

import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
	name="formular_case_expression",
	label="Expression condition multiple d'une formule",
	comodel=FormularExpression.class
)
public interface FormularCaseExpression extends FormularExpression {
	
	WhenCases cases() throws IOException;	
	ExpressionArg defaultValue() throws IOException;
}
