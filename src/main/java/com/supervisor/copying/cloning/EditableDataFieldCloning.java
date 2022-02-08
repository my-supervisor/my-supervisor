package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.copying.AbstractEditableDataFieldWriter;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public class EditableDataFieldCloning extends AbstractEditableDataFieldWriter {

	public EditableDataFieldCloning(DataSheetModel targetModel, EditableDataField source, Map<UUID, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataModelCloning(source, target, dataModelMappings).copy();
	}

	@Override
	protected void copyListDataFieldSources(ListDataField source, ListDataField target) throws IOException {
		target.sources().remove();
		for (ListDataFieldSource lSource : source.sources().items()) {
			final DataModel model = lSource.model();
			if(dataModelMappings.containsKey(model.id()))
				continue;
			
			final DataModel newModel = dataModelMappings.get(model.id());
			new ListDataFieldSourceCloning(target, newModel, lSource).copy();
		}
	}
	
}
