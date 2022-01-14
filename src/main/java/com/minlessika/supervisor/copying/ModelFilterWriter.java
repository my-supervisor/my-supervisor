package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ModelFilterCondition;
import com.minlessika.supervisor.domain.Writer;

public final class ModelFilterWriter implements Writer<ModelFilter> {

	private final ModelFilter source;
	private final AggregatedModel targetModel;
	
	public ModelFilterWriter(final AggregatedModel targetModel, final ModelFilter source) {
		this.source = source;
		this.targetModel = targetModel;
	}

	@Override
	public ModelFilter copy() throws IOException {
		
		final ModelFilter copy = targetModel.filters().add();
		for (ModelFilterCondition condition : source.conditions().items()) {
			copy.conditions()
			         .add(
		        		 targetModel.fields().get(condition.field().code()), 
		        		 condition.comparator(), 
		        		 condition.value()
			         );		
		}	
		
		return copy;
	}

}
