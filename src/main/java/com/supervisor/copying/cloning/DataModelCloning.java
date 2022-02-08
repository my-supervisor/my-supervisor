package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.copying.AbstractDataModelWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;

public final class DataModelCloning extends AbstractDataModelWriter {
	
	public DataModelCloning(String code, Activity targetActivity, DataModel source, Map<UUID, DataModel> dataModelMappings) {
		super(code, targetActivity, source, dataModelMappings);
	}
	
	public DataModelCloning(Activity targetActivity, DataModel source, Map<UUID, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataModelCloning(final DataModel source, final DataModel target, final Map<UUID, DataModel> dataModelMappings) {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataSheetModel copyDataSheetModel(Activity copy, DataSheetModel source) throws IOException {
		return new DataSheetModelCloning(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataSheetModelCloning(source, target, dataModelMappings).copy();
	}

	@Override
	protected AggregatedModel copyAggregatedModel(DataModel copy, AggregatedModel source) throws IOException {
		return new AggregatedModelCloning(code, targetActivity, copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyAggregatedModel(AggregatedModel source, AggregatedModel target) throws IOException {
		new AggregatedModelCloning(source, target, dataModelMappings).copy();
	}
}
