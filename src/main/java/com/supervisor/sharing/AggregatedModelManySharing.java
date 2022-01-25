package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.impl.PxAggregatedModel;

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
