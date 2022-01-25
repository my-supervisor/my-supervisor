package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.copying.ExpressionArgCopyImpl;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgCopy;
import com.supervisor.domain.FormularSimpleExpression;

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
