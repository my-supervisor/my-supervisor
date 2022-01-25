package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkParams;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.MappedDataField;
import com.supervisor.domain.MappedDataFields;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.impl.IndicatorBase;
import com.supervisor.indicator.impl.TypedDataModel;

public final class PxDataLink extends DomainRecordable<DataLink> implements DataLink {

	public PxDataLink(final Record<DataLink> record) throws IOException {
		super(record);
	}

	@Override
	public DataModel model() throws IOException {
		Record<DataModel> rec = record.of(DataLink::model);
		return TypedDataModel.convert(rec);
	}

	@Override
	public MappedDataFields mappings() throws IOException {
		return new PxMappedDataFields(
				record.listOf(MappedDataField.class), 
				this
		);
	}

	@Override
	public Indicator indicator() throws IOException {
		Record<Indicator> rec = record.of(DataLink::indicator);
		return new IndicatorBase(rec);
	}

	@Override
	public boolean active() throws IOException {
		return record.valueOf(DataLink::active);
	}

	@Override
	public void activate(boolean active) throws IOException {
		record.entryOf(DataLink::active, active)
		      .update();
	}

	@Override
	public DataLinkParams params() throws IOException {
		return new PgDataLinkParams(this);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(DataLink::name);
	}

	@Override
	public void update(String newName) throws IOException {
		
		record.isRequired(DataLink::name, newName);

		record.entryOf(DataLink::name, newName)
		      .update();
	}
	
	@Override
	public boolean interacting() throws IOException {
		return model().interacting() || !indicator().activity().id().equals(model().activity().id());
	}

	@Override
	public Activity activity() throws IOException {
		return indicator().activity();
	}

	@Override
	public DataDomainDefinition dataDomainDefinition() throws IOException {
		return record.valueOf(DataLink::dataDomainDefinition);
	}

	@Override
	public void update(DataDomainDefinition ddf) throws IOException {
		record.entryOf(DataLink::dataDomainDefinition, ddf)
		      .update();		
	}
}
