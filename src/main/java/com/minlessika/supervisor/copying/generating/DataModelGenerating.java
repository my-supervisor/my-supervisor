package com.minlessika.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractDataModelWriter;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;

public final class DataModelGenerating extends AbstractDataModelWriter {
	
	public DataModelGenerating(String code, Activity targetActivity, DataModel source, Map<Long, DataModel> dataModelMappings) {
		super(code, targetActivity, source, dataModelMappings);
	}
	
	public DataModelGenerating(Activity targetActivity, DataModel source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}
	
	public DataModelGenerating(final DataModel source, final DataModel target, final Map<Long, DataModel> dataModelMappings) {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataSheetModel copyDataSheetModel(Activity copy, DataSheetModel source) throws IOException {
		return new DataSheetModelGenerating(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyDataSheetModel(DataSheetModel source, DataSheetModel target) throws IOException {
		new DataSheetModelGenerating(source, target, dataModelMappings).copy();
	}

	@Override
	protected AggregatedModel copyAggregatedModel(DataModel copy, AggregatedModel source) throws IOException {
		return new AggregatedModelGenerating(code, targetActivity, copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyAggregatedModel(AggregatedModel source, AggregatedModel target) throws IOException {
		new AggregatedModelGenerating(source, target, dataModelMappings).copy();
	}
}
