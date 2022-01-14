package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractDataSheetModelWriter;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;

public final class DataSheetModelCloning extends AbstractDataSheetModelWriter {

	public DataSheetModelCloning(Activity targetActivity, DataSheetModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataSheetModelCloning(final DataSheetModel source, final DataSheetModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}	
}
