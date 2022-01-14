package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractFormularDataFieldWriter;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;

public final class FormularDataFieldCloning extends AbstractFormularDataFieldWriter {

	public FormularDataFieldCloning(AggregatedModel targetModel, FormularDataField source, Map<Long, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected DataSheetModel targetModelOf(DataSheetModel sourceModel) throws IOException {
		final DataSheetModel target;
		if(sourceModel.activity().id().equals(source.model().activity().id())) {
			target = (DataSheetModel)dataModelMappings.get(sourceModel.id());
		} else {
			throw new IllegalArgumentException("You can't clone an external model !");
		}
		
		return target;
	}

	@Override
	protected void copyFormularExtendedToParentExpressionOf(FormularDataField targetFormular, FormularExtendedToParentExpression source) throws IOException {
		new FormularExtendedToParentExpressionCloning(targetFormular, source).copy();
	}

	@Override
	protected void copyFormularExtendedToModelExpressionOf(FormularDataField targetFormular, FormularExtendedToModelExpression source) throws IOException {
		new FormularExtendedToModelExpressionCloning(targetFormular, source, dataModelMappings).copy();
	}

}
