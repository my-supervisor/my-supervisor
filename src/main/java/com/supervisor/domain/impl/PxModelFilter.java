package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ModelFilterCondition;
import com.supervisor.domain.ModelFilterConditions;

public final class PxModelFilter extends DomainRecordable<ModelFilter> implements ModelFilter {

	public PxModelFilter(final Record<ModelFilter> record) throws IOException {
		super(record);
	}

	@Override
	public AggregatedModel model() throws IOException {
		Record<AggregatedModel> rec = record.of(ModelFilter::model);
		return new PxAggregatedModel(rec); 
	}

	@Override
	public ModelFilterConditions conditions() throws IOException {
		return new PxModelFilterConditions(
				record.listOf(ModelFilterCondition.class), 
				this
		);
	}

}
