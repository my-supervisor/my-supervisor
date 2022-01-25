package com.supervisor.indicator.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.indicator.IndicatorTypeParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;
import com.supervisor.indicator.IndicatorTypeStaticParam;

public final class PxIndicatorTypeStaticParam extends IndicatorTypeParamWrap implements IndicatorTypeStaticParam {

	private final Record<IndicatorTypeStaticParam> rec;
	
	public PxIndicatorTypeStaticParam(IndicatorTypeParam origin) throws IOException {
		super(origin);
		
		if(origin.category() != IndicatorTypeParamCategory.STATIC)
			throw new IllegalArgumentException("Le paramètre doit être statique !");
		
		this.rec = origin.listOf(IndicatorTypeStaticParam.class).get(origin.id());
	}

	@Override
	public List<String> predefinedValues() throws IOException {
		return rec.valueOf(IndicatorTypeStaticParam::predefinedValues);
	}

	@Override
	public void predefine(List<String> values) throws IOException {
		
		rec.entryOf(IndicatorTypeStaticParam::predefinedValues, values)
			  .update();
	}

}
