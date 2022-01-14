package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.DataDomainDefinition;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataLinks;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.indicator.Indicator;

public final class PxDataLinks extends DomainRecordables<DataLink, DataLinks> implements DataLinks {

	private final Indicator indicator;
	
	public PxDataLinks(final RecordSet<DataLink> source, final Indicator indicator) throws IOException {
		super(PxDataLink.class, source);
		
		this.indicator = indicator;
		this.source = source.where(DataLink::indicator, indicator.id())
						    .orderBy(DataLink::id);
	}

	@Override
	protected DataLinks domainSetOf(final RecordSet<DataLink> source) throws IOException {
		return new PxDataLinks(source, indicator);
	}

	@Override
	public DataLink add(String name, DataModel model) throws IOException {
		
		source.isRequired(DataLink::name, name);
		
		Record<DataLink> record = source.entryOf(DataLink::model, model.id())
		                                .entryOf(DataLink::indicator, indicator.id())
		                                .entryOf(DataLink::active, true)
		                                .entryOf(DataLink::name, name)
		                                .entryOf(DataLink::dataDomainDefinition, DataDomainDefinition.ALL_PERIOD)
		                                .add();
		
		return domainOf(record);
	}

	@Override
	public DataLinks actives() throws IOException {
		return where(DataLink::active, true);
	}
}
