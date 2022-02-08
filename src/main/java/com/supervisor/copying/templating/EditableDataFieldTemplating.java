package com.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.copying.AbstractEditableDataFieldWriter;
import com.supervisor.sharing.ListDataFieldSourceSharing;
import com.supervisor.sharing.ListDataFieldSourceManySharing;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public final class EditableDataFieldTemplating extends AbstractEditableDataFieldWriter {

	public EditableDataFieldTemplating(DataSheetModel targetModel, EditableDataField source, Map<UUID, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataModelTemplating(source, target, dataModelMappings).copy();
	}

	@Override
	protected void copyListDataFieldSources(ListDataField source, ListDataField target) throws IOException {
		
		final Activity lSourceActivity = source.model().activity();
		
		// remove unused lsource
		for (ListDataFieldSource newLSource : target.sources().items()) {
			if(new ListDataFieldSourceManySharing(newLSource, lSourceActivity).isEmpty()) {
				target.sources().remove(newLSource);
			}
		}
		
		for (ListDataFieldSource lSource : source.sources().items()) {
			
			final DataModel lSourceModel = lSource.model();
			
			final ListDataFieldSourceSharing sharing = new ListDataFieldSourceManySharing(lSource);
			if(sharing.isEmpty()) {
				final DataModel targetDataModel;
				if(lSource.interacting()) {
					final Activity actor = lSourceModel.activity();
					final ActivityTemplate lSourceModelActivityTemplate = actor.templateSrc();
					if(lSourceModelActivityTemplate == ActivityTemplate.EMPTY)
						continue;
					
					if(!lSourceModelActivityTemplate.version().equals(actor.version()))
						throw new IllegalArgumentException(String.format("Pour continuer cette release, le modèle %s doit être mis à jour !", lSourceModelActivityTemplate.name()));
					
					final DataModels sourceDataModelTemplates = lSourceModelActivityTemplate.dataModels().where(DataModel::code, lSourceModel.code());
					targetDataModel = sourceDataModelTemplates.first();
				} else {
					targetDataModel = dataModelMappings.get(lSourceModel.id());					
				}
				
				new ListDataFieldSourceTemplating(target, targetDataModel, lSource).copy();
			} else {
				final ListDataFieldSource newLSource = sharing.concrete();
				new ListDataFieldSourceTemplating(lSource, newLSource).copy();
			}
		}
	}
}
