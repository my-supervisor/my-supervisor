package com.minlessika.supervisor.copying.cloning;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceCloning extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceCloning(FormularExtendedToModelExpression targetExpr, DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}
}
