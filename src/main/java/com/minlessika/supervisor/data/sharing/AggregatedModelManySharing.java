package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.impl.PxAggregatedModel;

public final class AggregatedModelManySharing extends DataSharingBase<AggregatedModel, AggregatedModelShared> implements AggregatedModelSharing {

	public AggregatedModelManySharing(final AggregatedModel concrete) {
		super(AggregatedModel.class, AggregatedModelShared.class, concrete);
	}
	
	public AggregatedModelManySharing(final AggregatedModel template, final Activity concreteActivity) {
		super(AggregatedModel.class, AggregatedModelShared.class, template, concreteActivity);
	}

	@Override
	public AggregatedModel concrete() throws IOException {
		return new PxAggregatedModel(concreteRecord());
	}

	@Override
	public AggregatedModel template() throws IOException {
		return new PxAggregatedModel(templateRecord());
	}
}
