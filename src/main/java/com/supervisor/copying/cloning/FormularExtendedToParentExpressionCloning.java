package com.supervisor.copying.cloning;

import java.io.IOException;

import com.supervisor.copying.AbstractFormularExtendedToParentExpressionWriter;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;

public final class FormularExtendedToParentExpressionCloning extends AbstractFormularExtendedToParentExpressionWriter {

	public FormularExtendedToParentExpressionCloning(final FormularDataField targetFormular, final FormularExtendedToParentExpression source) {
		super(targetFormular, source);
	}

	@Override
	protected void copyExtendedToParentSources(FormularExtendedToParentExpression targetExpr) throws IOException {
		for (FormularExtendedToParentSource src : source.sources().items()) {
			new FormularExtendedToParentSourceCloning(targetExpr, targetFormular.model().activity(), src).copy();
		}		
	}
	
}
