package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.supervisor.copying.AbstractDataSheetModelWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;

public final class DataSheetModelCloning extends AbstractDataSheetModelWriter {

	public DataSheetModelCloning(Activity targetActivity, DataSheetModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataSheetModelCloning(final DataSheetModel source, final DataSheetModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}	
}
