package com.supervisor.copying.templating;

import java.io.IOException;
import java.util.Map;

import com.supervisor.copying.AbstractAggregatedModelWriter;
import com.supervisor.sharing.AggregatedModelSharing;
import com.supervisor.sharing.AggregatedModelUniqueSharing;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;

public class AggregatedModelTemplating extends AbstractAggregatedModelWriter {

	public AggregatedModelTemplating(final String code, final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(code, targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelTemplating(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelTemplating(AggregatedModel source, AggregatedModel target, Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}
	
	@Override
	public AggregatedModel copy() throws IOException {
		final AggregatedModel copy = super.copy();
		
		if(copy.interacting()) {
			copy.activate(false);
		}
		
		copy.listOf(DataModel.class)
		    .get(copy.id())
		    .entryOf(DataModel::isTemplate, true)
		    .update();
		
		new AggregatedModelUniqueSharing(source).linkTo(copy);
		return copy;
	}
	
	@Override
	protected AggregatedModel findTarget(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final AggregatedModel target) throws IOException {
		if(target == AggregatedModel.EMPTY) {
			final AggregatedModelSharing sharing = new AggregatedModelUniqueSharing(source);
			if(sharing.isEmpty())
				return AggregatedModel.EMPTY;
			else
				return sharing.counterpart();
		}			
		else {
			return target;
		}			
	}
}
