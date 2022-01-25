package com.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;

import com.supervisor.domain.impl.UserOf;
import com.supervisor.copying.AbstractFormularExtendedToModelExpressionWriter;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.impl.PgActivities;

public final class FormularExtendedToModelExpressionGenerating extends AbstractFormularExtendedToModelExpressionWriter {

	public FormularExtendedToModelExpressionGenerating(FormularDataField targetFormular, FormularExtendedToModelExpression source, Map<Long, DataModel> dataModelMappings) {
		super(targetFormular, source, dataModelMappings);
	}

	@Override
	protected void copyExtendedToModelSources(FormularExtendedToModelExpression targetExpr) throws IOException {
		
		for (FormularExtendedToModelSource src : source.sources().items()) {
			
			final DataSheetModel dataModel = src.model();
			
			if(src.interacting()) {
				final Activity templateActor = dataModel.activity();
				final Activities actors = new PgActivities(new UserOf(targetExpr)).ownActivities().where(Activity::templateSrc, templateActor.id());
				if(actors.isEmpty())
					continue;
				
				for (Activity actor : actors.items()) {												
					if(!templateActor.version().equals(actor.version()))
						throw new IllegalArgumentException(String.format("Pour continuer cette mise à jour, vous devez mettre à jour l'activité %s !", actor.name()));
					
					final DataSheetModel targetModel = actor.forms().where(DataSheetModel::code, dataModel.code()).first();
					new FormularExtendedToModelSourceGenerating(targetExpr, targetModel, src).copy();
				}
			} else {
				final DataSheetModel targetModel = (DataSheetModel)targetExpr.formular().model().activity().dataModels().where(DataModel::code, dataModel.code()).first();
				new FormularExtendedToModelSourceGenerating(targetExpr, targetModel, src).copy();
			}			
		}
	}

}
