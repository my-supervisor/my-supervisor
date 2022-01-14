package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;
import com.minlessika.supervisor.indicator.IndicatorTypeStaticParam;
import com.minlessika.supervisor.indicator.IndicatorTypeStaticParams;

public final class PxIndicatorTypeStaticParams extends DomainRecordables<IndicatorTypeStaticParam, IndicatorTypeStaticParams> implements IndicatorTypeStaticParams {

	private final IndicatorType indicatorType;
	
	public PxIndicatorTypeStaticParams(final IndicatorType indicatorType) throws IOException {
		this(indicatorType.listOf(IndicatorTypeStaticParam.class), indicatorType);
	}
	
	public PxIndicatorTypeStaticParams(final RecordSet<IndicatorTypeStaticParam> source, final IndicatorType indicatorType) throws IOException {
		super(PxIndicatorTypeStaticParam.class, source);

		this.indicatorType = indicatorType;
		this.source = source.where(IndicatorTypeStaticParam::indicatorType, indicatorType.id())
				            .where(IndicatorTypeStaticParam::category, IndicatorTypeParamCategory.STATIC)
				            .orderBy(IndicatorTypeStaticParam::id);
	}
	
	@Override
	protected IndicatorTypeStaticParams domainSetOf(final RecordSet<IndicatorTypeStaticParam> source) throws IOException {
		return new PxIndicatorTypeStaticParams(source, indicatorType);
	}
	
	@Override
	protected IndicatorTypeStaticParam domainOf(final Record<IndicatorTypeStaticParam> record) throws IOException {
		return new PxIndicatorTypeStaticParam(
				new PxIndicatorTypeParam(
						record.of(IndicatorTypeParam.class, record.id())
				)
		);			
	}

	@Override
	public IndicatorTypeStaticParam add(int order, String name, String code, DataFieldType type) throws IOException {
		IndicatorTypeParam param = new PxIndicatorTypeParams(indicatorType).add(order, name, IndicatorTypeParamCategory.STATIC, code, type);
		return new PxIndicatorTypeStaticParam(param);
	}

	@Override
	public IndicatorTypeStaticParam get(String code) throws IOException {
		return where(IndicatorTypeStaticParam::code, code).first();
	}

	@Override
	public boolean isValidCode(String code) throws IOException {
		return where(IndicatorTypeStaticParam::code, code).any();
	}	
}
