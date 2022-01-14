package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;

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
