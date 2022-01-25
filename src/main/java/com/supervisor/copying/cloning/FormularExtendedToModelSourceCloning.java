package com.supervisor.copying.cloning;

import com.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceCloning extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceCloning(FormularExtendedToModelExpression targetExpr, DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}
}
