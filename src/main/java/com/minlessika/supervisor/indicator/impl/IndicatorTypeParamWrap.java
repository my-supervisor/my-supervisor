package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;

public class IndicatorTypeParamWrap extends DomainRecordable<IndicatorTypeParam> implements IndicatorTypeParam {

	private final IndicatorTypeParam origin;
	
	public IndicatorTypeParamWrap(final IndicatorTypeParam origin) throws IOException {
		super(origin.listOf(IndicatorTypeParam.class).get(origin.id()));
		
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
	
}
