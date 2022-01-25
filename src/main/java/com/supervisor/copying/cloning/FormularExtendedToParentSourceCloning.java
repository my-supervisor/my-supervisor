package com.supervisor.copying.cloning;

import java.io.IOException;

import com.supervisor.copying.AbstractFormularExtendedToParentSourceWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataFieldSource;

public final class FormularExtendedToParentSourceCloning extends AbstractFormularExtendedToParentSourceWriter {
	
	public FormularExtendedToParentSourceCloning(final FormularExtendedToParentExpression targetExpr, final Activity targetListSourceActivity, final FormularExtendedToParentSource source) {
		super(targetExpr, targetListSourceActivity, source);
	}
	
	public FormularExtendedToParentSourceCloning(final FormularExtendedToParentExpression targetExpr, final ListDataFieldSource targetListDataSource, final FormularExtendedToParentSource source) throws IOException {
		super(targetExpr, targetListDataSource, source);
	}

	@Override
	protected ListDataFieldSource targetListSourceOf(ListDataFieldSource sourceListDataFieldSource) throws IOException {
		throw new IllegalArgumentException("Cloning not supported !");
	}
	
}
