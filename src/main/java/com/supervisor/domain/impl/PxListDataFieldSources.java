package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.ListDataFieldSources;

public final class PxListDataFieldSources extends DomainRecordables<ListDataFieldSource, ListDataFieldSources> implements ListDataFieldSources {

	private final ListDataField field;
	
	public PxListDataFieldSources(final ListDataField field) throws IOException {
		this(field.listOf(ListDataFieldSource.class), field);
	}
	
	public PxListDataFieldSources(final RecordSet<ListDataFieldSource> source, final ListDataField field) throws IOException {
		super(PxListDataFieldSource.class, source);
		
		this.field = field;
		this.source = source.where(ListDataFieldSource::field, field.id())
						    .orderBy(ListDataFieldSource::creationDate);
	}

	@Override
	protected ListDataFieldSources domainSetOf(final RecordSet<ListDataFieldSource> source) throws IOException {
		return new PxListDataFieldSources(source, field);
	}
	
	@Override
	public ListDataFieldSource add(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException {
		
		if(model.interacting())
			throw new IllegalArgumentException(String.format("You can't use model (%s) that is interacting to build a list data source !", model.name()));
		
		source.mustCheckThisCondition(
			!model.id().equals(field.model().id()), 
			"Le modèle doit être différent des modèles aggrégés du modèle du champ !"
		);
		
		source.mustCheckThisCondition(
			where(ListDataFieldSource::model, model.id()).isEmpty(), 
			"Ce modèle a déjà été utilisé dans cette liste !"
		);
		
		Record<ListDataFieldSource> record = source.entryOf(ListDataFieldSource::model, model.id())
												   .entryOf(ListDataFieldSource::field, field.id())
												   .entryOf(ListDataFieldSource::active, true)
												   .entryOf(ListDataFieldSource::fieldToDisplay, fieldToDisplay.id())
												   .entryOf(ListDataFieldSource::orderField, orderField.id())
			      								   .add();

		return domainOf(record);
	}

	@Override
	public ListDataFieldSources actives() throws IOException {
		return where(ListDataFieldSource::active, true);
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		
		for (ListDataFieldSource src : items()) {
			if(src.isBasedOn(model))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		
		for (ListDataFieldSource src : items()) {
			if(src.isStrictBasedOn(model))
				return true;
		}
		
		return false;
	}

	@Override
	public ListDataFieldSource whichBasedOn(DataModel model) throws IOException {
		for (ListDataFieldSource src : items()) {
			if(src.isBasedOn(model))
				return src;
		}
		
		throw new IllegalArgumentException(String.format("List data field source based on model %s not found !", model.name()));
	}
}
