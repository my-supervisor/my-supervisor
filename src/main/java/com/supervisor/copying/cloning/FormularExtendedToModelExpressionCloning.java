package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.copying.AbstractFormularExtendedToModelExpressionWriter;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelExpressionCloning extends AbstractFormularExtendedToModelExpressionWriter {

	public FormularExtendedToModelExpressionCloning(FormularDataField targetFormular, FormularExtendedToModelExpression source, Map<UUID, DataModel> dataModelMappings) {
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
