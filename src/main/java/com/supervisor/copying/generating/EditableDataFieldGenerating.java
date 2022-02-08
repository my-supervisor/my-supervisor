package com.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.impl.UserOf;
import com.supervisor.copying.AbstractEditableDataFieldWriter;
import com.supervisor.sharing.ListDataFieldSourceSharing;
import com.supervisor.sharing.ListDataFieldSourceUniqueSharing;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.impl.PgActivities;

public final class EditableDataFieldGenerating extends AbstractEditableDataFieldWriter {
	
	public EditableDataFieldGenerating(DataSheetModel targetModel, EditableDataField source, Map<UUID, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataModelGenerating(source, target, dataModelMappings).copy();
	}

	@Override
	protected void copyListDataFieldSources(ListDataField source, ListDataField target) throws IOException {
		
		// remove unused lsource
		for (ListDataFieldSource newLSource : target.sources().items()) {
			if(new ListDataFieldSourceUniqueSharing(newLSource).isEmpty()) {
				target.sources().remove(newLSource);
			}
		}
		
		for (ListDataFieldSource lSource : source.sources().items()) {
			
			final DataModel lSourceModel = lSource.model();
			final Activity targetActivity = target.model().activity();
			final ListDataFieldSourceSharing sharing = new ListDataFieldSourceUniqueSharing(lSource, targetActivity, targetActivity);
			if(sharing.isEmpty()) {
				if(lSource.interacting()) {
					final Activity templateActor = lSourceModel.activity();
					final Activities actors = new PgActivities(new UserOf(target)).ownActivities().where(Activity::templateSrc, templateActor.id());
					if(actors.isEmpty())
						continue;
					
					for (Activity actor : actors.items()) {												
						if(!templateActor.version().equals(actor.version()))
							throw new IllegalArgumentException(String.format("Pour continuer cette mise à jour, vous devez mettre à jour l'activité %s !", actor.name()));
						
						final DataModels actorDataModels = actor.dataModels().where(DataModel::code, lSourceModel.code());
						final DataModel targetDataModel = actorDataModels.first();
						new ListDataFieldSourceGenerating(target, targetDataModel, lSource).copy();
					}
				} else {
					final DataModel targetDataModel = dataModelMappings.get(lSourceModel.id());
					new ListDataFieldSourceGenerating(target, targetDataModel, lSource).copy();
				}
			} else {
				final ListDataFieldSource newLSource = sharing.concrete();
				new ListDataFieldSourceGenerating(lSource, newLSource).copy();
			}
		}
	}
}
