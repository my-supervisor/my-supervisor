package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.AggregatedModels;
import com.minlessika.supervisor.domain.DataFields;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExpressionType;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.indicator.impl.PxIndicators;
import com.minlessika.supervisor.indicator.impl.TypedDataModel;

public class PxDataModel extends DomainRecordable<DataModel> implements DataModel {

	public PxDataModel(Record<DataModel> record) {
		super(record);
	}

	@Override
	public Activity activity() throws IOException {
		return new PxActivity(
					record.of(DataModel::activity)
			   );
	}

	@Override
	public DataModelType type() throws IOException {
		return record.valueOf(DataModel::type);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(DataModel::code);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(DataModel::name);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(DataModel::description);
	}

	@Override
	public DataFields fields() throws IOException {
		return new PgDataFields(TypedDataModel.convert(this));
	}

	@Override
	public boolean active() throws IOException {
		return record.valueOf(DataModel::active);
	}

	@Override
	public void update(String code, String name, String description) throws IOException {
		
		record.isRequired(DataModel::code, code);
		record.isRequired(DataModel::name, name);
		
		record.mustBeUnique(DataModel::code, code, DataModel::activity);
		
		record.entryOf(DataModel::code, code)
		      .entryOf(DataModel::name, name)
		      .entryOf(DataModel::description, description)
		      .update();
	}

	@Override
	public void activate(boolean active) throws IOException {
		record.entryOf(DataModel::active, active)
	      	  .update();
	}

	@Override
	public boolean isTemplate() throws IOException {
		return record.valueOf(DataModel::isTemplate);
	}

	@Override
	public void templating(boolean template) throws IOException {
		record.entryOf(DataModel::isTemplate, template)
    	      .update();
	}

	@Override
	public Indicators indicatorsThatDependsOn() throws IOException {
		final String viewScript = String.format(
										"( \r\n" + 
										"	select DISTINCT ON (indic.id) indic.* \r\n" + 
										"	from %s as indic \r\n" + 
										"	left join %s as link on link.indicator_id = indic.id \r\n" + 
										"	where link.active = true and (link.model_id = %s or link.model_id in (select id from %s where model_id=%s)) \r\n"+ 
										") as %s \r\n",
										new TableImpl(Indicator.class).name(),
										new TableImpl(DataLink.class).name(),							
										id(),
										new TableImpl(AggregatedModel.class).name(),
										id(),
										new TableImpl(Indicator.class).name()
								 );

		RecordSet<Indicator> records = base().select(Indicator.class, viewScript);
		return new PxIndicators(records);
	}

	private boolean dependsOn(DataModel model, boolean strictly) throws IOException {
		
		// check if it is aggregatedmodel of model
		if(type() == DataModelType.AGGREGATED_MODEL) {
			final AggregatedModel currentAmodel = new PxAggregatedModel(this);
			if(currentAmodel.model().id().equals(model.id())) {
				return true;
			} // you can do later for not strictly case		
		}
		
		// check if its lists are based on model
		for (ListDataField list : fields().lists().items()) {
			if(strictly && list.isStrictBasedOn(model) || !strictly && list.isBasedOn(model)) {
				return true;
			}
		}
		
		// check if lists of its tables (except parent list) are base on model
		for (TableDataField table : model.fields().tables().items()) {			
			for(ListDataField field : table.structure().fields().lists().items()) {
				if(!field.code().equals("PARENT")) {
					for (ListDataFieldSource source : field.sources().items()) {
						if(strictly && source.isStrictBasedOn(model) || !strictly && source.isBasedOn(model)) {
							return true;
						}
					}
				}
			}
		}
		
		if(type() == DataModelType.AGGREGATED_MODEL) {
			final AggregatedModel currentAmodel = new PxAggregatedModel(this);
			
			// take dependencies of formulars
			for (FormularDataField formular : currentAmodel.formulars().items()) {
				for (FormularExpression expr : formular.expressions().items()) {
					if(expr.type() == FormularExpressionType.EXTENDED_TO_PARENT) {
						final FormularExtendedToParentExpression extParent = (FormularExtendedToParentExpression)expr;
						for (FormularExtendedToParentSource parentSource : extParent.sources().items()) {
							if(strictly && parentSource.isStrictBasedOn(model) || !strictly && parentSource.isBasedOn(model)) {
								return true;
							}
						}			
					} else if (expr.type() == FormularExpressionType.EXTENDED_TO_CHILD) {
						final FormularExtendedToChildExpression extChild = (FormularExtendedToChildExpression)expr;
						if(extChild.child().model().id().equals(model.id())) {
							return true;
						}
					} else if(expr.type() == FormularExpressionType.EXTENDED_TO_MODEL) {
						final FormularExtendedToModelExpression extParent = (FormularExtendedToModelExpression)expr;
						for (FormularExtendedToModelSource source : extParent.sources().items()) {
							if(strictly && source.isStrictBasedOn(model) || !strictly && source.isBasedOn(model)) {
								return true;
							}
						}			
					}
				}
			}
		}	
		
		return false;
	}
	
	@Override
	public boolean dependsOn(DataModel model) throws IOException {
		return dependsOn(model, false);
	}

	@Override
	public boolean strictDependsOn(DataModel model) throws IOException {
		return dependsOn(model, true);
	}

	@Override
	public AggregatedModels aggregatedModels() throws IOException {
		return activity().aggregatedModels()
				         .where(AggregatedModel::model, id());
	}
	
	@Override
	public boolean interacting() throws IOException {
		return record.valueOf(DataModel::interacting);
	}
}
