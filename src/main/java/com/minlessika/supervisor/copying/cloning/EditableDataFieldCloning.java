package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractEditableDataFieldWriter;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;

public class EditableDataFieldCloning extends AbstractEditableDataFieldWriter {

	public EditableDataFieldCloning(DataSheetModel targetModel, EditableDataField source, Map<Long, DataModel> dataModelMappings) {
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
