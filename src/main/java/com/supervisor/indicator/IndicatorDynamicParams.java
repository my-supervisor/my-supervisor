package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.domain.bi.AggregateFunc;

public interface IndicatorDynamicParams extends DomainSet<IndicatorDynamicParam, IndicatorDynamicParams> {

	IndicatorDynamicParam put(final IndicatorTypeDynamicParam origin, final AggregateFunc aggregateFunc) throws IOException;
	IndicatorDynamicParam get(String code) throws IOException;
	
	boolean isValidCode(String code) throws IOException;
	
}
