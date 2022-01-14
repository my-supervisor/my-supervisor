package com.minlessika.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractEditableDataFieldWriter;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceSharing;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceManySharing;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModels;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;

public final class EditableDataFieldTemplating extends AbstractEditableDataFieldWriter {

	public EditableDataFieldTemplating(DataSheetModel targetModel, EditableDataField source, Map<Long, DataModel> dataModelMappings) {
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
