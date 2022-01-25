package com.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.supervisor.copying.AbstractDataSheetModelWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;

public final class DataSheetModelTemplating extends AbstractDataSheetModelWriter {

	public DataSheetModelTemplating(Activity targetActivity, DataSheetModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataSheetModelTemplating(final DataSheetModel source, final DataSheetModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}
	
	@Override
	public DataSheetModel copy() throws IOException {
		final DataSheetModel copy = super.copy();
		
		copy.listOf(DataModel.class)
		    .get(copy.id())
		    .entryOf(DataModel::isTemplate, true)
		    .update();
		
		return copy;
	}
}
