package com.minlessika.supervisor.copying.generating;

import java.io.IOException;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToParentSourceWriter;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceUniqueSharing;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.ListDataFieldSource;

public final class FormularExtendedToParentSourceGenerating extends AbstractFormularExtendedToParentSourceWriter {

	public FormularExtendedToParentSourceGenerating(final FormularExtendedToParentExpression targetExpr, final Activity targetListSourceModelActivity, final FormularExtendedToParentSource source) {
		super(targetExpr, targetListSourceModelActivity, source);
	}
	
	public FormularExtendedToParentSourceGenerating(final FormularExtendedToParentExpression targetExpr, final ListDataFieldSource targetListDataSource, final FormularExtendedToParentSource source) throws IOException {
		super(targetExpr, targetListDataSource, source);
	}

	@Override
	protected ListDataFieldSource targetListSourceOf(ListDataFieldSource sourceListDataFieldSource) throws IOException {
		return new ListDataFieldSourceUniqueSharing(sourceListDataFieldSource, targetListSourceModelActivity, targetExpr.formular().model().activity()).counterpart();
	}
	
}
