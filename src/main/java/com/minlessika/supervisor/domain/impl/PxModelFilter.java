package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ModelFilterCondition;
import com.minlessika.supervisor.domain.ModelFilterConditions;

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
