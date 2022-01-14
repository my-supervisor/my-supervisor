package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypes;

public final class PxIndicatorTypes extends DomainRecordables<IndicatorType, IndicatorTypes> implements IndicatorTypes {
	
	public PxIndicatorTypes(final RecordSet<IndicatorType> source) throws IOException {
		super(PxIndicatorType.class, source);
	}

	@Override
	public IndicatorType add(String name, String shortName, int defaultSizeX, int defaultSizeY, String description) throws IOException {
		
		source.isRequired(IndicatorType::name, name);
		source.isRequired(IndicatorType::shortName, shortName);
		source.isRequired(IndicatorType::description, description);
		
		Record<IndicatorType> record = source.entryOf(IndicatorType::name, name)
											 .entryOf(IndicatorType::shortName, shortName)
											 .entryOf(IndicatorType::description, description)
											 .entryOf(IndicatorType::defaultSizeX, defaultSizeX)
											 .entryOf(IndicatorType::defaultSizeY, defaultSizeY)
											 .add();

		return domainOf(record);
	}

	@Override
	public IndicatorType indicatorType(String shortName) throws IOException {
		return where(IndicatorType::shortName, shortName).first();
	}
}
