package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgCopy;
import com.minlessika.supervisor.domain.FormularCaseExpression;

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
