package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.indicator.IndicatorTypeDynamicParam;
import com.supervisor.indicator.IndicatorTypeParam;
import com.supervisor.indicator.IndicatorTypeParamCategory;

public final class PxIndicatorTypeDynamicParam extends IndicatorTypeParamWrap implements IndicatorTypeDynamicParam {

	public PxIndicatorTypeDynamicParam(IndicatorTypeParam origin) throws IOException {
		super(origin);
		
		if(origin.category() != IndicatorTypeParamCategory.DYNAMIC)
			throw new IllegalArgumentException("Le paramètre doit être dynamique !");
	}	
}
