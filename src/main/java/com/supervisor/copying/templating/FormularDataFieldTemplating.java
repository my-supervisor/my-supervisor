package com.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.supervisor.domain.impl.OwnerOf;
import com.supervisor.copying.AbstractFormularDataFieldWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToParentExpression;

public final class FormularDataFieldTemplating extends AbstractFormularDataFieldWriter {

	public FormularDataFieldTemplating(AggregatedModel targetModel, FormularDataField source, Map<Long, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected DataSheetModel targetModelOf(DataSheetModel sourceModel) throws IOException {
		final DataSheetModel target;
		if(sourceModel.activity().id().equals(source.model().activity().id())) {
			target = (DataSheetModel)dataModelMappings.get(sourceModel.id());
		} else {
			final Activity sourceTemplate = sourceModel.activity().templateSrc();
			if(sourceTemplate == ActivityTemplate.EMPTY)
				throw new IllegalArgumentException(String.format("You should create template of %s before continuing !", sourceModel.name()));
			
			target = sourceTemplate.formsOf(new OwnerOf(sourceModel)).where(DataSheetModel::code, sourceModel.code()).first();
		}
		
		return target;
	}
	
	@Override
	protected void copyFormularExtendedToParentExpressionOf(FormularDataField targetFormular, FormularExtendedToParentExpression source) throws IOException {
		new FormularExtendedToParentExpressionTemplating(targetFormular, source).copy();
	}

	@Override
	protected void copyFormularExtendedToModelExpressionOf(FormularDataField targetFormular, FormularExtendedToModelExpression source) throws IOException {
		new FormularExtendedToModelExpressionTemplating(targetFormular, source, dataModelMappings).copy();
	}
}
