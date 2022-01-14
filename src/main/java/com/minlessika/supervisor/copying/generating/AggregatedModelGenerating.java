package com.minlessika.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractAggregatedModelWriter;
import com.minlessika.supervisor.data.sharing.AggregatedModelSharing;
import com.minlessika.supervisor.data.sharing.AggregatedModelUniqueSharing;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;

public final class AggregatedModelGenerating extends AbstractAggregatedModelWriter {
	
	public AggregatedModelGenerating(final String code, final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(code, targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelGenerating(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(targetActivity, targetModel, source, dataModelMappings);
	}
	
	public AggregatedModelGenerating(final AggregatedModel source, final AggregatedModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}
	
	@Override
	public AggregatedModel copy() throws IOException {
		final AggregatedModel copy = super.copy();
		if(target == AggregatedModel.EMPTY) {
			if(copy.interacting() && !targetModel.activity().interactsWith(copy.activity())) {
				copy.activate(false);
			}			
		}
		new AggregatedModelUniqueSharing(source, copy.model().activity(), copy.activity()).linkTo(copy);
		return copy;
	}
	
	@Override
	protected AggregatedModel findTarget(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final AggregatedModel target) throws IOException {
		if(target == AggregatedModel.EMPTY) {
			AggregatedModelSharing sharing = new AggregatedModelUniqueSharing(source, targetModel.activity(), targetActivity);
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
