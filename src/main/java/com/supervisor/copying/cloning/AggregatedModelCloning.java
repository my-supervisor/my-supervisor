package com.supervisor.copying.cloning;

import java.io.IOException;
import java.util.Map;

import com.supervisor.copying.AbstractAggregatedModelWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;

public class AggregatedModelCloning extends AbstractAggregatedModelWriter {

	public AggregatedModelCloning(final String code, final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(code, targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelCloning(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelCloning(AggregatedModel source, AggregatedModel target, Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}
	
	@Override
	public AggregatedModel copy() throws IOException {
		final AggregatedModel copy = super.copy();
		copy.activate(source.active());
		return copy;
	}

	@Override
	protected AggregatedModel findTarget(Activity targetActivity, DataModel targetModel, AggregatedModel source, AggregatedModel target) throws IOException {
		return target;
	}
}
