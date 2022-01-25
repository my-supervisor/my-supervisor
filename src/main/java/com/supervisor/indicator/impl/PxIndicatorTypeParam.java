package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataFieldType;
import com.supervisor.indicator.IndicatorTypeParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;
import com.supervisor.indicator.IndicatorType;

public final class PxIndicatorTypeParam extends DomainRecordable<IndicatorTypeParam> implements IndicatorTypeParam {

	public PxIndicatorTypeParam(final Record<IndicatorTypeParam> source) throws IOException {
		super(source);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(IndicatorTypeParam::name);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(IndicatorTypeParam::code);
	}

	@Override
	public DataFieldType type() throws IOException {
		return record.valueOf(IndicatorTypeParam::type);
	}

	@Override
	public IndicatorType indicatorType() throws IOException {
		Record<IndicatorType> rec = record.of(IndicatorTypeParam::indicatorType);
		return new PxIndicatorType(rec);
	}

	@Override
	public IndicatorTypeParamCategory category() throws IOException {
		return record.valueOf(IndicatorTypeParam::category);
	}

	@Override
	public int order() throws IOException {
		return record.valueOf(IndicatorTypeParam::order);
	}
}
