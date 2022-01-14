package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.supervisor.domain.DataFieldType;

public interface IndicatorTypeDynamicParams extends DomainSet<IndicatorTypeDynamicParam, IndicatorTypeDynamicParams> {
	
	IndicatorTypeDynamicParam add(int order, String name, String code, DataFieldType type) throws IOException;
	IndicatorTypeDynamicParam get(String code) throws IOException;
	
	boolean isValidCode(String code) throws IOException;
}
