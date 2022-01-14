package com.minlessika.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.supervisor.copying.AbstractFormularExtendedToModelExpressionWriter;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;

public final class FormularExtendedToModelExpressionTemplating extends AbstractFormularExtendedToModelExpressionWriter {

	public FormularExtendedToModelExpressionTemplating(FormularDataField targetFormular, FormularExtendedToModelExpression source, final Map<Long, DataModel> dataModelMappings) {
		super(targetFormular, source, dataModelMappings);
	}

	@Override
	protected void copyExtendedToModelSources(FormularExtendedToModelExpression targetExpr) throws IOException {		
		for (FormularExtendedToModelSource src : source.sources().items()) {
			
			final DataSheetModel dataModel = src.model();
			final DataSheetModel targetModel;
			if(src.interacting()) {
				final Activity actor = dataModel.activity();
				final ActivityTemplate activityTemplate = actor.templateSrc();
				if(activityTemplate == ActivityTemplate.EMPTY)
					continue;
									
				if(!activityTemplate.version().equals(actor.version()))
					throw new IllegalArgumentException(String.format("Pour continuer cette release, le modèle %s doit être mis à jour !", activityTemplate.name()));
				
				targetModel = activityTemplate.formsOf(new OwnerOf(activityTemplate)).where(DataSheetModel::code, dataModel.code()).first();
				new FormularExtendedToModelSourceTemplating(targetExpr, targetModel, src).copy();
			} else {
				targetModel = (DataSheetModel)dataModelMappings.get(dataModel.id());
				new FormularExtendedToModelSourceTemplating(targetExpr, targetModel, src).copy();
			}			
		}
	}

}
