package com.supervisor.copying.templating;

import com.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceTemplating extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceTemplating(FormularExtendedToModelExpression targetExpr, final DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}
}
