package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.supervisor.copying.ExpressionArgCopyImpl;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgCopy;
import com.minlessika.supervisor.domain.FormularSimpleExpression;

public final class SimpleFormularExpressionArgCopyImpl implements ExpressionArgCopy {

	final FormularSimpleExpression expr;
	final ExpressionArg arg;
	
	public SimpleFormularExpressionArgCopyImpl(final FormularSimpleExpression expr, final ExpressionArg arg) {
		this.expr = expr;
		this.arg = arg;
	}
	
	@Override
	public void execute() throws IOException {
		new ExpressionArgCopyImpl(arg, expr.arg(arg.no())).execute();
	}

}
