package com.minlessika.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractDataSheetWriter;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;

public final class DataSheetCloning extends AbstractDataSheetWriter {

	public DataSheetCloning(final DataSheetModel targetModel, final DataSheet source, final Map<Long, DataModel> dataModelMappings) {
		super(targetModel, source, dataModelMappings);
	}
	
	public DataSheetCloning(DataSheet source, DataSheet target, Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataFieldOfSheet copyDataFieldOfSheet(DataSheet copy, DataFieldOfSheet source) throws IOException {
		return new DataFieldOfSheetCloning(copy, source, dataModelMappings).copy();
	}

	@Override
	protected DataFieldOfSheet copyDataFieldOfSheet(DataFieldOfSheet source, DataFieldOfSheet target) throws IOException {
		return new DataFieldOfSheetCloning(source, target, dataModelMappings).copy();
	}	
}
