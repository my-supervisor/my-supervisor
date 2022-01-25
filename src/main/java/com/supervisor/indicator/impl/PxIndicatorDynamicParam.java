package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypeDynamicParam;
import com.supervisor.indicator.IndicatorTypeParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;

public final class PxIndicatorDynamicParam extends DomainRecordable<IndicatorDynamicParam> implements IndicatorDynamicParam {

	public PxIndicatorDynamicParam(final Record<IndicatorDynamicParam> source) throws IOException {
		super(source);
	}

	@Override
	public int order() throws IOException {
		return origin().order();
	}

	@Override
	public String name() throws IOException {
		return origin().name();
	}

	@Override
	public String code() throws IOException {
		return origin().code();
	}

	@Override
	public DataFieldType type() throws IOException {
		return origin().type();
	}

	@Override
	public IndicatorType indicatorType() throws IOException {
		return indicator().type();
	}

	@Override
	public Indicator indicator() throws IOException {
		Record<Indicator> rec = record.of(IndicatorDynamicParam::indicator);
		return new IndicatorBase(rec);
	}

	@Override
	public IndicatorTypeDynamicParam origin() throws IOException {
		
		Long id = record.valueOf(IndicatorDynamicParam::origin);
		Record<IndicatorTypeParam> rec = record.of(IndicatorTypeParam.class, id);
		
		return new PxIndicatorTypeDynamicParam(
			new PxIndicatorTypeParam(rec)
		);
	}

	@Override
	public AggregateFunc aggregateFunc() throws IOException {
		return record.valueOf(IndicatorDynamicParam::aggregateFunc);
	}
	
	@Override
	public void update(AggregateFunc func) throws IOException {
		
		record.entryOf(IndicatorDynamicParam::aggregateFunc, func)
			  .update();
	}

	@Override
	public IndicatorTypeParamCategory category() throws IOException {
		return origin().category();
	}
}
