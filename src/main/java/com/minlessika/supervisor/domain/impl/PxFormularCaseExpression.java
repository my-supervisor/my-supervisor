package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgs;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.WhenCases;

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
