package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.ExpressionArgCopy;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.WhenCase;

public final class CaseFormularWhenExpressionArgCopyImpl implements ExpressionArgCopy {

	final FormularCaseExpression expr;
	final WhenCase wcase;
	
	public CaseFormularWhenExpressionArgCopyImpl(final FormularCaseExpression expr, final WhenCase wcase) {
		this.expr = expr;
		this.wcase = wcase;
	}
	
	@Override
	public void execute() throws IOException {
		
		WhenCase newCase = expr.cases().add();
		
		new ExpressionArgCopyImpl(wcase.leftArg(), newCase.leftArg()).execute();
		newCase.updateComparator(wcase.comparator());
		new ExpressionArgCopyImpl(wcase.rightArg(), newCase.rightArg()).execute();
		new ExpressionArgCopyImpl(wcase.result(), newCase.result()).execute();		
	}

}
