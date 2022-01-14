package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;

public final class PxIndicatorDynamicNumberParam extends DomainRecordable<IndicatorDynamicNumberParam> implements IndicatorDynamicNumberParam {

	private final IndicatorDynamicParam origin;
	
	public PxIndicatorDynamicNumberParam(final IndicatorDynamicParam origin) throws IOException {
		super(origin.listOf(IndicatorDynamicNumberParam.class).get(origin.id()));
		
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
	public int precision() throws IOException {
		return record.valueOf(IndicatorDynamicNumberParam::precision);
	}

	@Override
	public boolean applyThousandsSeparator() throws IOException {
		return record.valueOf(IndicatorDynamicNumberParam::applyThousandsSeparator);
	}

	@Override
	public void update(int precision, boolean applyThousandsSeparator) throws IOException {
		
		record.mustCheckThisCondition(
				precision >= 0 && precision <= 5, 
				"Pour la précision, entrez un nombre compris entre 0 et 5."
		);
		
		record.entryOf(IndicatorDynamicNumberParam::precision, precision)
		      .entryOf(IndicatorDynamicNumberParam::applyThousandsSeparator, applyThousandsSeparator)
		      .update();
	}
}
