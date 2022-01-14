package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ExpressionArgs extends DomainSet<ExpressionArg, ExpressionArgs> {
	ExpressionArg add(int no) throws IOException;
	ExpressionArg addDataFieldArg(int no) throws IOException;
}
