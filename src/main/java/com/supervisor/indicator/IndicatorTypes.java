package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface IndicatorTypes extends DomainSet<IndicatorType, IndicatorTypes> {
	IndicatorType add(String name, String shortName, int defaultSizeX, int defaultSizeY, String description) throws IOException;
	
	IndicatorType indicatorType(String shortName) throws IOException;
}
