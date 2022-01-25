package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.IndicatorDynamicStringParam;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypeDynamicParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;

public final class PxIndicatorDynamicStringParam extends DomainRecordable<IndicatorDynamicStringParam> implements IndicatorDynamicStringParam {

	private final IndicatorDynamicParam origin;
	
	public PxIndicatorDynamicStringParam(final IndicatorDynamicParam origin) throws IOException {
		super(origin.listOf(IndicatorDynamicStringParam.class).get(origin.id()));
		
		this.origin = origin;
	}

	@Override
	public IndicatorTypeParamCategory category() throws IOException {
		return origin.category();
	}

	@Override
	public int order() throws IOException {
		return origin.order();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public String code() throws IOException {
		return origin.code();
	}

	@Override
	public DataFieldType type() throws IOException {
		return origin.type();
	}

	@Override
	public IndicatorType indicatorType() throws IOException {
		return origin.indicatorType();
	}

	@Override
	public Indicator indicator() throws IOException {
		return origin.indicator();
	}

	@Override
	public IndicatorTypeDynamicParam origin() throws IOException {
		return origin.origin();
	}

	@Override
	public AggregateFunc aggregateFunc() throws IOException {
		return origin.aggregateFunc();
	}

	@Override
	public void update(AggregateFunc func) throws IOException {
		origin.update(func); 
	}

	@Override
	public String markup() throws IOException {
		return record.valueOf(IndicatorDynamicStringParam::markup);
	}

	@Override
	public void update(String markup) throws IOException {
		
		record.entryOf(IndicatorDynamicStringParam::markup, markup)
		      .update();
	}
}
