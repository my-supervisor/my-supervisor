package com.minlessika.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractDataSheetModelWriter;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;

public final class DataSheetModelGenerating extends AbstractDataSheetModelWriter {

	public DataSheetModelGenerating(Activity targetActivity, DataSheetModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataSheetModelGenerating(final DataSheetModel source, final DataSheetModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}	
}
