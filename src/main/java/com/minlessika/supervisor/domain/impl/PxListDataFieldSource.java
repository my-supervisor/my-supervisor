package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.indicator.impl.TypedDataModel;

public final class PxListDataFieldSource extends DomainRecordable<ListDataFieldSource> implements ListDataFieldSource {

	public PxListDataFieldSource(Record<ListDataFieldSource> record) {
		super(record);
	}

	@Override
	public ListDataField field() throws IOException {
		final Long fieldId = record.valueOf(ListDataFieldSource::field);
		return new PxListDataField(
					record.of(DataField.class, fieldId)
			   );
	}

	@Override
	public DataModel model() throws IOException {
		return TypedDataModel.convert(record.of(ListDataFieldSource::model));
	}

	@Override
	public DataField fieldToDisplay() throws IOException {
		final Long fieldId = record.valueOf(ListDataFieldSource::fieldToDisplay);
		return TypedDataField.convert(
				record.of(DataField.class, fieldId)
			   );
	}

	@Override
	public DataField orderField() throws IOException {
		final Long fieldId = record.valueOf(ListDataFieldSource::orderField);
		return TypedDataField.convert(
				record.of(DataField.class, fieldId)
			   );
	}

	@Override
	public void update(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException {
		
		final DataModel fieldModel = field().model();
		
		record.mustCheckThisCondition(
			!fieldModel.id().equals(model.id()), 
			"Le modèle ne doit pas dépendre du modèle du champ !"
		);
		
		record.entryOf(ListDataFieldSource::model, model.id())
			  .entryOf(ListDataFieldSource::fieldToDisplay, fieldToDisplay.id())
			  .entryOf(ListDataFieldSource::orderField, orderField.id())
			  .update();
	}

	@Override
	public boolean active() throws IOException {
		return record.valueOf(ListDataFieldSource::active);
	}

	@Override
	public void activate(boolean active) throws IOException {
		record.entryOf(ListDataFieldSource::active, active)
	      	  .update();
	}

	@Override
	public Activity activity() throws IOException {
		return field().model().activity();
	}

	@Override
	public boolean interacting() throws IOException {
			return !field().model().activity().id().equals(model().activity().id());
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		
		final DataModel srcModel = model();
		
		if(srcModel.id().equals(model.id())) {
			return true;
		}
		
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			final DataSheetModel sheetModel = new PxDataSheetModel(model);
			for (AggregatedModel aModel : sheetModel.aggregatedModels().items()) {
				if(srcModel.id().equals(aModel.id())) {
					return true;
				}
			}
		} else if(model.type() == DataModelType.AGGREGATED_MODEL) {
			return isBasedOn(new PxAggregatedModel(model).model());
		}
		
		return false;
	}

	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		return model().id().equals(model.id());
	}
	
}
