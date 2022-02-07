package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetOfModels;

public final class PxDataSheetOfModels extends DomainRecordables<DataSheet, DataSheetOfModels> implements DataSheetOfModels {

	private final DataSheetModel model;
	
	public PxDataSheetOfModels(final DataSheetModel model) throws IOException {
		this(model.listOf(DataSheet.class), model);
	}
	
	public PxDataSheetOfModels(final RecordSet<DataSheet> source, final DataSheetModel model) throws IOException {
		super(PxDataSheet.class, source);
		
		this.model = model;
		this.source = source.where(DataSheet::model, model.id());
	}
	
	@Override
	protected DataSheetOfModels domainSetOf(final RecordSet<DataSheet> source) throws IOException {
		return new PxDataSheetOfModels(source, model);
	}

	@Override
	public DataSheet add(String reference, LocalDate date) throws IOException {
		
		new UserOf(this).profile().validateAccessibility("NEW_DATA_SHEET");
		
		source.mustCheckThisCondition(
				model.active(), 
				String.format("Le modèle %s n'est pas actif !", model.name())
		);
		
		source.mustCheckThisCondition(
			where(DataSheet::reference, reference).isEmpty(), 
			String.format("Data sheet reference (%s) already exists !", reference)
		);
		
		Record<DataSheet> record = source.entryOf(DataSheet::model, model.id())
									     .entryOf(DataSheet::reference, reference)
									     .entryOf(DataSheet::date, date)
									     .entryOf(DataSheet::ownerId, model.ownerId())
									     .addForUser(model.ownerId());
		
		return domainOf(record);
	}

	@Override
	public DataSheet copy(DataSheet source) throws IOException {
		
		/*final Map<Long, DataModel> targetDataModels = new HashMap<>();
		targetDataModels.put(source.model().id(), model);
		final List<DataModel> sourceDataModelsThatDependsOn = source.model().dataModelDependencies().items();
		final List<DataModel> targetDataModelsThatDependsOn = model.dataModelDependencies().items();
		for (int i = 0; i < sourceDataModelsThatDependsOn.size(); i++) {
			targetDataModels.put(sourceDataModelsThatDependsOn.get(i).id(), targetDataModelsThatDependsOn.get(i)); 
		}
		
		return new DataSheetCloning(model, source, targetDataModels).copy();*/
		
		throw new UnsupportedOperationException("DataSheetOfModels#copy"); 
	}
}
