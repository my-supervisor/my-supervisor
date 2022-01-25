package com.supervisor.copying;

import java.io.IOException;

import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgCopy;
import com.supervisor.domain.FormularCaseExpression;

public final class CaseFormularDefaultExpressionArgCopyImpl implements ExpressionArgCopy {

	private final FormularCaseExpression expr;
	private final ExpressionArg arg;
	
	public CaseFormularDefaultExpressionArgCopyImpl(final FormularCaseExpression expr, final ExpressionArg arg) {
		this.expr = expr;
		this.arg = arg;
	}
	
	@Override
	public void execute() throws IOException {
		new ExpressionArgCopyImpl(arg, expr.defaultValue()).execute();
	}

}
