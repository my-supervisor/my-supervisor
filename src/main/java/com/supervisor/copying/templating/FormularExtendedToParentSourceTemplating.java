package com.supervisor.copying.templating;

import java.io.IOException;

import com.supervisor.copying.AbstractFormularExtendedToParentSourceWriter;
import com.supervisor.sharing.ListDataFieldSourceUniqueSharing;
import com.supervisor.domain.Activity;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataFieldSource;

public final class FormularExtendedToParentSourceTemplating extends AbstractFormularExtendedToParentSourceWriter {

	public FormularExtendedToParentSourceTemplating(final FormularExtendedToParentExpression targetExpr, final Activity targetListSourceActivity, final FormularExtendedToParentSource source) {
		super(targetExpr, targetListSourceActivity, source);
	}
	
	public FormularExtendedToParentSourceTemplating(final FormularExtendedToParentExpression targetExpr, final ListDataFieldSource targetListDataSource, final FormularExtendedToParentSource source) throws IOException {
		super(targetExpr, targetListDataSource, source);
	}
	
	@Override
	protected ListDataFieldSource targetListSourceOf(ListDataFieldSource sourceListDataFieldSource) throws IOException {
		return new ListDataFieldSourceUniqueSharing(sourceListDataFieldSource).counterpart();
	}
	
}
