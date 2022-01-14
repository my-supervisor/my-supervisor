package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.supervisor.domain.DataFieldType;

public interface IndicatorTypeParams extends DomainSet<IndicatorTypeParam, IndicatorTypeParams> {
	IndicatorTypeParam add(int order, String name, IndicatorTypeParamCategory category, String code, DataFieldType type) throws IOException;
	IndicatorTypeParam get(String code) throws IOException;
	
	boolean isValidCode(String code) throws IOException;
}
