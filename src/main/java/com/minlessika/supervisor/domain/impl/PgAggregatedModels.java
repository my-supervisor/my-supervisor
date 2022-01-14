package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.AggregatedModels;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;

public final class PgAggregatedModels extends DomainRecordables<AggregatedModel, AggregatedModels> implements AggregatedModels {

	private final Activity activity;
	
	public PgAggregatedModels(final Activity activity) throws IOException {
		this(viewSource(activity), activity);
	}
	
	private PgAggregatedModels(final RecordSet<AggregatedModel> source, final Activity activity) throws IOException {
		super(PxAggregatedModel.class, source);
		
		this.activity = activity;
		this.source = source.where(AggregatedModel::activity, activity.id());
	}
	
	@Override
	protected AggregatedModels domainSetOf(final RecordSet<AggregatedModel> source) throws IOException {
		return new PgAggregatedModels(source, activity);
	}
	
	private static RecordSet<AggregatedModel> viewSource(final Activity activity) throws IOException {
		
		Table tableModel = new TableImpl(AggregatedModel.class);
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select am.*, dmodel.activity_id as core_activity_id, core.interacting, core.code, core.type, core.active, core.activity_id, core.name, core.description, core.is_template \r\n" + 
								"   from %s am \r\n" +
								"   left join %s model on model.id = am.model_id \r\n" +
								"   left join %s dmodel on dmodel.id = model.id \r\n" +
								"   left join %s core on core.id = am.id \r\n" +
								") as %s",
								tableModel.name(),
								new TableImpl(DataSheetModel.class).name(),
								new TableImpl(DataModel.class).name(),
								new TableImpl(DataModel.class).name(),
								tableModel.name()
							);
		
		return activity.base()
				   	   .select(AggregatedModel.class, viewScript)
				   	   .orderBy(AggregatedModel::id, OrderDirection.DESC);
	}

	@Override
	public AggregatedModel add(String code, DataModel model) throws IOException {
		
		if(model.interacting())
			throw new IllegalArgumentException(String.format("You can't use model (%s) that is interacting to build another model !", model.name()));
		
		final DataModel item = new PgDataModels(activity).add(
									code, 
									model.name(), 
									DataModelType.AGGREGATED_MODEL, 
									StringUtils.EMPTY
							   );
		
		final DataModel coreModel;
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			coreModel = model;
		} else {
			coreModel = ((AggregatedModel)model).coreModel();
		}
		
		source.of(DataModel.class)
		      .get(item.id())
		      .entryOf(DataModel::interacting, !activity.equals(model.activity()))
		      .update();
		
		Record<AggregatedModel> record = source.entryOf(AggregatedModel::id, item.id())
											   .entryOf(AggregatedModel::model, model.id())
											   .entryOf(AggregatedModel::coreModel, coreModel.id())
											   .entryOf(AggregatedModel::dateReference, model.fields().get("DATE").id())
				                               .add();
		
		return domainOf(record);
	}
	
	@Override
	public void remove(AggregatedModel item) throws IOException{
		new PgDataModels(activity).remove(item);
	}
	
	@Override
	public boolean contains(String code) throws IOException {
		return where(AggregatedModel::code, code).any();
	}
}
