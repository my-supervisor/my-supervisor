package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.domain.DataFieldType;

public interface IndicatorTypeStaticParams extends DomainSet<IndicatorTypeStaticParam, IndicatorTypeStaticParams> {
	
	IndicatorTypeStaticParam add(int order, String name, String code, DataFieldType type) throws IOException;
	IndicatorTypeStaticParam get(String code) throws IOException;
	
	boolean isValidCode(String code) throws IOException;
}
