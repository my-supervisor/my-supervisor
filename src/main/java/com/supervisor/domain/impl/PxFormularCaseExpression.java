package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgs;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.WhenCases;

public final class PxFormularCaseExpression extends PxBasicFormularExpression implements FormularCaseExpression {

	public PxFormularCaseExpression(Record<FormularExpression> record) throws IOException {
		super(record);
	}

	@Override
	public WhenCases cases() throws IOException {
		return new PxWhenCases(this);
	}

	@Override
	public ExpressionArg defaultValue() throws IOException {
		return arguments().where(ExpressionArg::no, 0).first(); 
	}

	@Override
	public String toText() throws IOException {
		return String.format("CASE (NB_CASE=%s, DEFAULT_VALUE=%s)", cases().count(), defaultValue().name());
	}

	@Override
	public ExpressionArgs arguments() throws IOException {
		return new PxCaseExpressionArgs(this);
	}

}
