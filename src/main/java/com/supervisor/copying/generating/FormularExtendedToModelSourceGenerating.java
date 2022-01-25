package com.supervisor.copying.generating;

import com.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceGenerating extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceGenerating(FormularExtendedToModelExpression targetExpr, DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}

}
