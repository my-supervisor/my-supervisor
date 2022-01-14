package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.ActivityParams;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.ParamDataField;

public final class PgActivityParams extends DomainRecordables<ActivityParam, ActivityParams> implements ActivityParams {

	private final Activity activity;
	
	public PgActivityParams(final Activity activity) throws IOException {
		this(viewSource(activity), activity);
	}
	
	public PgActivityParams(final RecordSet<ActivityParam> source, final Activity activity) throws IOException {
		super(PxActivityParam.class, source);
		
		this.activity = activity;
		this.source = source.where(ActivityParam::activity, activity.id())
				            .orderBy(ActivityParam::id);
	}
	
	@Override
	protected ActivityParams domainSetOf(final RecordSet<ActivityParam> source) throws IOException {
		return new PgActivityParams(source, activity);
	}
	
	private static RecordSet<ActivityParam> viewSource(final Activity activity) throws IOException{
		Table table = new TableImpl(ActivityParam.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select src.*, target.code, target.model_id, target.name \r\n" + 
					                        "   from %s as src \r\n" + 
											"	left join %s as target on target.id = src.id \r\n" +
										  ") as %s",
										table.name(),
										new TableImpl(DataField.class).name(),										
										table.name()
							);
		
		return activity.base()
				       .select(ActivityParam.class, viewScript);
	}
	
	@Override
	public ActivityParam put(ParamDataField param, String value) throws IOException {
		
		ActivityParam lparam = null;
		
		ActivityParams lparams = this.where(ActivityParam::id, param.id())
				                     .where(ActivityParam::activity, activity.id());
		
		if(lparams.any()) {
			lparam = lparams.first();
			lparam.update(value); 
		}else {
			// créer le paramètre
			source.isRequired(ActivityParam::value, value);
			
			List<DataModel> models = activity.dataModels().items();
			
			boolean isBelong = false;
			for (DataModel model : models) {
				if(model.fields().params().contains(param)) {
					isBelong = true;
					break;
				}
			}
			
			if(!isBelong)
				throw new IllegalArgumentException(String.format("Le paramètre %s ne figure pas parmi les paramètres des modèles agrégés !", param.name()));
			
			new DataFieldValueImpl(param, value).validate();
			
			Record<ActivityParam> record = source.entryOf(ActivityParam::id, param.id())
										         .entryOf(ActivityParam::activity, activity.id())
										         .entryOf(ActivityParam::value, new DataFieldValueImpl(param, value).cleaned())
										         .add();

			lparam = domainOf(record);
		}
		
		return lparam;
	}
}
