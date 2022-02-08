package com.supervisor.indicator.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataFieldType;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorStaticParam;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypeParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;
import com.supervisor.indicator.IndicatorTypeStaticParam;

public final class PxIndicatorStaticParam extends DomainRecordable<IndicatorStaticParam> implements IndicatorStaticParam {

	public PxIndicatorStaticParam(final Record<IndicatorStaticParam> source) throws IOException {
		super(source);
	}

	@Override
	public IndicatorTypeParamCategory category() throws IOException {
		return origin().category();
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
		Record<Indicator> rec = record.of(IndicatorStaticParam::indicator);
		return new IndicatorBase(rec);
	}

	@Override
	public IndicatorTypeStaticParam origin() throws IOException {
		final UUID id = record.valueOf(IndicatorStaticParam::origin);
		Record<IndicatorTypeParam> rec = record.of(IndicatorTypeParam.class, id);
		
		return new PxIndicatorTypeStaticParam(
			new PxIndicatorTypeParam(rec)
		);
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(IndicatorStaticParam::value);
	}

	@Override
	public List<String> predefinedValues() throws IOException {
		return origin().predefinedValues();
	}

	@Override
	public void update(String value) throws IOException {
		
		record.isRequired(IndicatorStaticParam::value, value);
		
		record.entryOf(IndicatorStaticParam::value, value)
		      .update();
	}

	@Override
	public void predefine(List<String> values) throws IOException {
		origin().predefine(values); 
	}
}
