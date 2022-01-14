package com.minlessika.supervisor.copying.templating;

import java.io.IOException;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToParentSourceWriter;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceUniqueSharing;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.ListDataFieldSource;

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
