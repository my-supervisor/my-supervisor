package com.supervisor.copying.generating;

import java.io.IOException;

import com.supervisor.domain.impl.UserOf;
import com.supervisor.copying.AbstractFormularExtendedToParentExpressionWriter;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.impl.PgActivities;

public final class FormularExtendedToParentExpressionGenerating extends AbstractFormularExtendedToParentExpressionWriter {

	public FormularExtendedToParentExpressionGenerating(final FormularDataField targetFormular, final FormularExtendedToParentExpression source) {
		super(targetFormular, source);
	}
	
	@Override
	protected void copyExtendedToParentSources(FormularExtendedToParentExpression targetExpr) throws IOException {
		
		for (FormularExtendedToParentSource src : source.sources().items()) {
			
			final DataModel dataModel = src.model();
			
			if(src.interacting()) {
				final Activity templateActor = dataModel.activity();
				final Activities actors = new PgActivities(new UserOf(targetExpr)).ownActivities().where(Activity::templateSrc, templateActor.id());
				if(actors.isEmpty())
					continue;
				
				for (Activity actor : actors.items()) {												
					if(!templateActor.version().equals(actor.version()))
						throw new IllegalArgumentException(String.format("Pour continuer cette mise à jour, vous devez mettre à jour l'activité %s !", actor.name()));
					
					new FormularExtendedToParentSourceGenerating(targetExpr, actor, src).copy();
				}
			} else {
				new FormularExtendedToParentSourceGenerating(targetExpr, targetFormular.model().activity(), src).copy();
			}			
		}		
	}
}
