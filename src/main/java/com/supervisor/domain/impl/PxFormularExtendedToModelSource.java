package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.indicator.impl.TypedDataModel;

public final class PxFormularExtendedToModelSource extends DomainRecordable<FormularExtendedToModelSource> implements FormularExtendedToModelSource {

	public PxFormularExtendedToModelSource(Record<FormularExtendedToModelSource> record) {
		super(record);
	}

	@Override
	public DataField reference() throws IOException {
		final UUID fieldId = record.valueOf(FormularExtendedToModelSource::reference);
		return TypedDataField.convert(record.of(DataField.class, fieldId));
	}

	@Override
	public EditableDataField modelField() throws IOException {
		final UUID fieldId = record.valueOf(FormularExtendedToModelSource::modelField);
		return (EditableDataField)TypedDataField.convert(record.of(DataField.class, fieldId));
	}
	
	@Override
	public boolean active() throws IOException {
		return record.valueOf(FormularExtendedToModelSource::active);
	}

	@Override
	public void update(EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend) throws IOException {
		
		final DataSheetModel model = model();
		
		record.mustCheckThisCondition(
			model.fields().contains(modelField), 
			String.format("Le champ %s doit appartenir au modèle %s !", modelField.name(), model.name())
		);
		
		record.mustCheckThisCondition(
			model.fields().contains(fieldToExtend), 
			String.format("Le champ %s doit appartenir au modèle %s !", fieldToExtend.name(), model.name())
		);
		
		final AggregatedModel expressionModel = expressionModel();
		
		record.mustCheckThisCondition(
			expressionModel.fields().contains(reference), 
			String.format("Le champ %s doit appartenir au modèle %s !", reference.name(), expressionModel.name())
		);
				
		record.entryOf(FormularExtendedToModelSource::modelField, modelField.id())
			  .entryOf(FormularExtendedToModelSource::comparator, comparator)
			  .entryOf(FormularExtendedToModelSource::reference, reference.id())
			  .entryOf(FormularExtendedToModelSource::fieldToExtend, fieldToExtend.id())
			  .update();
	}

	@Override
	public FormularExtendedToModelExpression expr() throws IOException {
		return new PxFormularExtendedToModelExpression(record.of(FormularExtendedToModelSource::expr));
	}

	@Override
	public boolean interacting() throws IOException {
		return record.valueOf(FormularExtendedToModelSource::interacting);
	}

	@Override
	public void activate(boolean active) throws IOException {
		record.entryOf(FormularExtendedToModelSource::active, active)
		      .update();
	}

	@Override
	public DataSheetModel model() throws IOException {
		final UUID modelId = record.valueOf(FormularExtendedToModelSource::model);
		return (DataSheetModel)TypedDataModel.convert(record.of(DataModel.class, modelId));
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

	@Override
	public AggregatedModel expressionModel() throws IOException {
		return expr().formular().model();
	}

	@Override
	public Comparator comparator() throws IOException {
		return record.valueOf(FormularExtendedToModelSource::comparator);
	}

	@Override
	public EditableDataField fieldToExtend() throws IOException {
		final UUID fieldId = record.valueOf(FormularExtendedToModelSource::fieldToExtend);
		return (EditableDataField)TypedDataField.convert(record.of(DataField.class, fieldId));
	}

}
