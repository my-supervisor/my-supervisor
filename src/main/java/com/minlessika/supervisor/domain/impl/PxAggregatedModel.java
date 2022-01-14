package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;
import com.minlessika.supervisor.domain.EditableDataFields;
import com.minlessika.supervisor.domain.DataFields;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldCompatibility;
import com.minlessika.supervisor.domain.ModelFilters;
import com.minlessika.supervisor.domain.FormularDataFields;
import com.minlessika.supervisor.domain.ParamDataFields;
import com.minlessika.supervisor.indicator.impl.TypedDataModel;

public final class PxAggregatedModel extends DataModelWrap implements AggregatedModel {

	private static final Logger logger = new MLogger(PxAggregatedModel.class);
	
	private final Record<AggregatedModel> record;

	public PxAggregatedModel(final Record<AggregatedModel> record) throws IOException {
		this(modelOf(record));
	}
	
	public PxAggregatedModel(final DataModel origin) throws IOException {
		super(origin);
		
		if(type() != DataModelType.AGGREGATED_MODEL)
			throw new IllegalArgumentException("Aggregated model waited !");
		
		this.record = origin.listOf(AggregatedModel.class).get(origin.id());
	}
	
	private static DataModel modelOf(Record<AggregatedModel> record) throws IOException {
		return new PxDataModel(record.listOf(DataModel.class).get(record.id()));
	}

	@Override
	public DataModel model() throws IOException {
		Record<DataModel> rec = record.of(AggregatedModel::model);
		return TypedDataModel.convert(rec);
	}

	@Override
	public DataFields fields() throws IOException {
		return new PgDataFields(this);
	}

	@Override
	public ModelFilters filters() throws IOException {
		return new PgModelFilters(this);
	}

	@Override
	public FormularDataFields formulars() throws IOException {
		return new PgFormularDataFields(this);
	}

	@Override
	public ParamDataFields params() throws IOException {
		return new PgParamDataFields(this);
	}

	@Override
	public List<DataSheetModel> compatibleModels() throws IOException {
		List<DataSheetModel> models = new ArrayList<>();
		
		User user = new UserOf(this);
		
		for (
				DataSheetModel model : new PgDataSheetModels(user)
											.where(DataSheetModel::isTemplate, false)
											.items()
			) 
		{
			if(isCompatibleWith(model)) {
				models.add(model);
			}			
		}
		
		return models;
	}

	@Override
	public List<DataFieldCompatibility> compatibilityOf(DataSheetModel model) throws IOException {
		List<DataFieldCompatibility> compatibility = new ArrayList<>();
		
		List<DataField> fieldsUsed = fields().items();
		List<DataField> fieldsOfModel = model.fields().items();
		
		for (DataField source : fieldsUsed) {
			
			List<DataField> targets = fieldsOfModel.stream().filter(c -> {
											try {
												return c.type() == source.type();
											} catch (IOException e) {
												logger.error(e);
												throw new RuntimeException(e);
											}
									  }).collect(Collectors.toList());
			
			compatibility.add(new RuleDataFieldCompatibilityImpl(source, targets));
		}
		
		return compatibility;
	}

	@Override
	public boolean isCompatibleWith(DataSheetModel model) throws IOException {
		return compatibilityOf(model).stream()
				                     .allMatch(c -> !c.targets().isEmpty());
	}

	@Override
	public void update(String name, DataField dateReference) throws IOException {
		
		record.mustCheckThisCondition(
				fields().contains(dateReference), 
				"Field doesn't belong to model !"
		);
		
		origin.update(code(), name, description());
		record.entryOf(AggregatedModel::dateReference, dateReference.id())
		      .update();		
	}

	@Override
	public EditableDataFields baseFields() throws IOException {
		return coreModel().scalarEditableFields();
	}

	@Override
	public DataSheetModel coreModel() throws IOException {
		return new PxDataSheetModel(record.of(AggregatedModel::coreModel));
	}

	@Override
	public Activity coreActivity() throws IOException {
		return coreModel().activity();
	}

	@Override
	public DataField dateReference() throws IOException {
		return TypedDataField.convert(record.of(AggregatedModel::dateReference));
	}
}
