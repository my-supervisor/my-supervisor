package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface IndicatorStaticParams extends DomainSet<IndicatorStaticParam, IndicatorStaticParams> {

	IndicatorStaticParam put(final IndicatorTypeStaticParam origin, final String value) throws IOException;
	IndicatorStaticParam get(String code) throws IOException;
	
	boolean isValidCode(String code) throws IOException;
}
