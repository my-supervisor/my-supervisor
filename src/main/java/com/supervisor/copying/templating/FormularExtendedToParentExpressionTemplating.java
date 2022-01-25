package com.supervisor.copying.templating;

import java.io.IOException;

import com.supervisor.copying.AbstractFormularExtendedToParentExpressionWriter;
import com.supervisor.sharing.ListDataFieldSourceUniqueSharing;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataFieldSource;

public final class FormularExtendedToParentExpressionTemplating extends AbstractFormularExtendedToParentExpressionWriter {

	public FormularExtendedToParentExpressionTemplating(final FormularDataField targetFormular, final FormularExtendedToParentExpression source) {
		super(targetFormular, source);
	}
	
	@Override
	protected void copyExtendedToParentSources(FormularExtendedToParentExpression targetExpr) throws IOException {
		
		for (FormularExtendedToParentSource src : source.sources().items()) {
			
			final DataModel dataModel = src.model();
			if(src.interacting()) {
				final Activity actor = dataModel.activity();
				final ActivityTemplate activityTemplate = actor.templateSrc();
				if(activityTemplate == ActivityTemplate.EMPTY)
					continue;
									
				if(!activityTemplate.version().equals(actor.version()))
					throw new IllegalArgumentException(String.format("Pour continuer cette release, le modèle %s doit être mis à jour !", activityTemplate.name()));
				
				final ListDataFieldSource targetListDataFieldSource = new ListDataFieldSourceUniqueSharing(src.listSource()).counterpart();
				new FormularExtendedToParentSourceTemplating(targetExpr, targetListDataFieldSource, src).copy();
			} else {
				new FormularExtendedToParentSourceTemplating(targetExpr, targetFormular.model().activity(), src).copy();
			}			
		}		
	}
}
