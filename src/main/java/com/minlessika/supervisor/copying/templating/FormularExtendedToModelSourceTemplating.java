package com.minlessika.supervisor.copying.templating;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceTemplating extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceTemplating(FormularExtendedToModelExpression targetExpr, final DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}
}
