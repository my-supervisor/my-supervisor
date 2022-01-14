package com.minlessika.supervisor.copying.generating;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToModelSourceWriter;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelSourceGenerating extends AbstractFormularExtendedToModelSourceWriter {

	public FormularExtendedToModelSourceGenerating(FormularExtendedToModelExpression targetExpr, DataSheetModel targetSourceModel, FormularExtendedToModelSource source) {
		super(targetExpr, targetSourceModel, source);
	}

}
