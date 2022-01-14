package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractFormularExtendedToModelExpressionWriter;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelExpressionCloning extends AbstractFormularExtendedToModelExpressionWriter {

	public FormularExtendedToModelExpressionCloning(FormularDataField targetFormular, FormularExtendedToModelExpression source, Map<Long, DataModel> dataModelMappings) {
		super(targetFormular, source, dataModelMappings);
	}

	@Override
	protected void copyExtendedToModelSources(FormularExtendedToModelExpression targetExpr) throws IOException {
		
		for (FormularExtendedToModelSource src : source.sources().items()) {
						
			if(!src.interacting()) {
				final DataSheetModel dataModel = src.model();
				final DataSheetModel targetModel = (DataSheetModel)dataModelMappings.get(dataModel.id());
				new FormularExtendedToModelSourceCloning(targetExpr, targetModel, src).copy();
			}			
		}
	}

}
