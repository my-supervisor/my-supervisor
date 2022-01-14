package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParams;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;

public final class PxIndicatorTypeDynamicParams extends DomainRecordables<IndicatorTypeDynamicParam, IndicatorTypeDynamicParams> implements IndicatorTypeDynamicParams {

	private final IndicatorType indicatorType;
	
	public PxIndicatorTypeDynamicParams(final IndicatorType indicatorType) throws IOException {
		this(indicatorType.listOf(IndicatorTypeDynamicParam.class), indicatorType);
	}
	
	public PxIndicatorTypeDynamicParams(final RecordSet<IndicatorTypeDynamicParam> source, final IndicatorType indicatorType) throws IOException {
		super(PxIndicatorTypeDynamicParam.class, source);

		this.indicatorType = indicatorType;
		this.source = source.where(IndicatorTypeDynamicParam::indicatorType, indicatorType.id())
						    .where(IndicatorTypeDynamicParam::category, IndicatorTypeParamCategory.DYNAMIC)
						    .orderBy(IndicatorTypeDynamicParam::id); 
	}
	
	@Override
	protected IndicatorTypeDynamicParams domainSetOf(final RecordSet<IndicatorTypeDynamicParam> source) throws IOException {
		return new PxIndicatorTypeDynamicParams(source, indicatorType);
	}
	
	@Override
	protected IndicatorTypeDynamicParam domainOf(final Record<IndicatorTypeDynamicParam> record) throws IOException {
		return new PxIndicatorTypeDynamicParam(
				new PxIndicatorTypeParam(
						record.of(IndicatorTypeParam.class, record.id())
				)
		);			
	}

	@Override
	public IndicatorTypeDynamicParam add(int order, String name, String code, DataFieldType type) throws IOException {
		IndicatorTypeParam param = new PxIndicatorTypeParams(indicatorType).add(order, name, IndicatorTypeParamCategory.DYNAMIC, code, type);
		return new PxIndicatorTypeDynamicParam(param);
	}

	@Override
	public IndicatorTypeDynamicParam get(String code) throws IOException {
		return where(IndicatorTypeDynamicParam::code, code).first();
	}

	@Override
	public boolean isValidCode(String code) throws IOException {
		return where(IndicatorTypeDynamicParam::code, code).any();
	}		
}
