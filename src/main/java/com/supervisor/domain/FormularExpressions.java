package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface FormularExpressions extends DomainSet<FormularExpression, FormularExpressions> {
	
	FormularSimpleExpression add(FormularFunc func) throws IOException;
	FormularCaseExpression addCase() throws IOException;
	FormularExtendedToParentExpression addParentExtension(ListDataField reference) throws IOException;
	FormularExtendedToChildExpression addChildExtension() throws IOException;
	FormularExtendedToModelExpression addModelExtension() throws IOException;
}
