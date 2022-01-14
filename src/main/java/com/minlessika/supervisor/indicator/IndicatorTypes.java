package com.minlessika.supervisor.indicator;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface IndicatorTypes extends DomainSet<IndicatorType, IndicatorTypes> {
	IndicatorType add(String name, String shortName, int defaultSizeX, int defaultSizeY, String description) throws IOException;
	
	IndicatorType indicatorType(String shortName) throws IOException;
}
