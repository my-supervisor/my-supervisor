package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypeDynamicParams;
import com.supervisor.indicator.IndicatorTypeParams;
import com.supervisor.indicator.IndicatorTypeStaticParams;

public final class PxIndicatorType extends DomainRecordable<IndicatorType> implements IndicatorType {

	public PxIndicatorType(final Record<IndicatorType> source) throws IOException {
		super(source);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(IndicatorType::name);
	}

	@Override
	public String shortName() throws IOException {
		return record.valueOf(IndicatorType::shortName);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(IndicatorType::description);
	}

	@Override
	public void update(String name, String shortName, int defaultSizeX, int defaultSizeY, String description) throws IOException {
		
		record.isRequired(IndicatorType::name, name);
		record.isRequired(IndicatorType::shortName, shortName);
		record.isRequired(IndicatorType::description, description);
		
		record.entryOf(IndicatorType::name, name)
		      .entryOf(IndicatorType::shortName, shortName)
			  .entryOf(IndicatorType::description, description)
			  .entryOf(IndicatorType::defaultSizeX, defaultSizeX)
			  .entryOf(IndicatorType::defaultSizeY, defaultSizeY)
			  .update();
	}

	@Override
	public int defaultSizeX() throws IOException {
		return record.valueOf(IndicatorType::defaultSizeX);
	}

	@Override
	public int defaultSizeY() throws IOException {
		return record.valueOf(IndicatorType::defaultSizeY);
	}

	@Override
	public IndicatorTypeParams params() throws IOException {
		return new PxIndicatorTypeParams(this);
	}

	@Override
	public IndicatorTypeStaticParams staticParams() throws IOException {
		return new PxIndicatorTypeStaticParams(this);
	}

	@Override
	public IndicatorTypeDynamicParams dynamicParams() throws IOException {
		return new PxIndicatorTypeDynamicParams(this);
	}
}
