package com.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.impl.UserOf;
import com.supervisor.copying.AbstractFormularDataFieldWriter;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.impl.PgActivities;

public final class FormularDataFieldGenerating extends AbstractFormularDataFieldWriter {

	public FormularDataFieldGenerating(AggregatedModel targetModel, FormularDataField source, Map<UUID, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected DataSheetModel targetModelOf(DataSheetModel sourceModel) throws IOException {
		
		final DataSheetModel target;
		
		if(sourceModel.activity().id().equals(source.model().activity().id())) {
			target = (DataSheetModel)dataModelMappings.get(sourceModel.id());
		} else {
			final Activities userActivities = new PgActivities(new UserOf(targetModel));
			final Activities actors = userActivities.ownActivities().where(Activity::templateSrc, sourceModel.activity().id());
			if(actors.isEmpty())
				throw new IllegalArgumentException(String.format("Vous devez créer une activité %s avant de continuer !", sourceModel.activity().name()));
			
			final Activity targetActivity = actors.first();
			if(!targetActivity.isUpToDate())
				throw new IllegalArgumentException(String.format("Vous devez mettre à jour l'activité %s avant de continuer !", targetActivity.name()));
			
			target = targetActivity.forms().where(DataSheetModel::code, sourceModel.code()).first();
		}
		
		return target;
	}

	@Override
	protected void copyFormularExtendedToParentExpressionOf(FormularDataField targetFormular, FormularExtendedToParentExpression source) throws IOException {
		new FormularExtendedToParentExpressionGenerating(targetFormular, source).copy();
	}

	@Override
	protected void copyFormularExtendedToModelExpressionOf(FormularDataField targetFormular, FormularExtendedToModelExpression source) throws IOException {
		new FormularExtendedToModelExpressionGenerating(targetFormular, source, dataModelMappings).copy();
	}
}
