package com.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.supervisor.copying.AbstractDataModelWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;

public final class DataModelTemplating extends AbstractDataModelWriter {

	public DataModelTemplating(String code, Activity targetActivity, DataModel source, Map<Long, DataModel> dataModelMappings) {
		super(code, targetActivity, source, dataModelMappings);
	}
	
	public DataModelTemplating(Activity targetActivity, DataModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataModelTemplating(final DataModel source, final DataModel target, final Map<Long, DataModel> dataModelMappings) {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataSheetModel copyDataSheetModel(Activity copy, DataSheetModel source) throws IOException {
		return new DataSheetModelTemplating(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataSheetModelTemplating(source, target, dataModelMappings).copy();
	}

	@Override
	protected AggregatedModel copyAggregatedModel(DataModel copy, AggregatedModel source) throws IOException {
		return new AggregatedModelTemplating(code, targetActivity, copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyAggregatedModel(AggregatedModel source, AggregatedModel target) throws IOException {
		new AggregatedModelTemplating(source, target, dataModelMappings).copy();
	}

	@Override
	public DataModel copy() throws IOException {
		final DataModel copy = super.copy();
		copy.listOf(DataModel.class)
	        .get(copy.id())
	        .entryOf(DataModel::isTemplate, true)
	        .update();
		return copy;
	}
}
