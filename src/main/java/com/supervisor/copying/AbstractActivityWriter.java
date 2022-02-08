package com.supervisor.copying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.User;
import com.supervisor.domain.UserScope;
import com.supervisor.domain.impl.OwnerOf;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.supervisor.sdk.datasource.conditions.pgsql.PgFilter;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityParam;
import com.supervisor.domain.ActivityParams;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.Writer;
import com.supervisor.domain.impl.DataFieldSortingOnDependency;
import com.supervisor.domain.impl.DataModelSortingOnDependency;
import com.supervisor.domain.impl.DataSheetModelTableWrappingImpl;
import com.supervisor.domain.impl.PgActivities;
import com.supervisor.indicator.Indicator;

public abstract class AbstractActivityWriter implements Writer<Activity> {

	protected final User user;
	protected final Activity source;
	protected final Activity target;
	protected final Map<UUID, DataModel> dataModelMappings = new HashMap<>();
	
	public AbstractActivityWriter(final Activity source, final Activity target) throws IOException {
		this(new OwnerOf(target), source, target);
	}
	
	public AbstractActivityWriter(final User user, final Activity source) {
		this(user, source, Activity.EMPTY);
	}

	private AbstractActivityWriter(final User user, final Activity source, final Activity target) {
		this.user = user;
		this.source = source;
		this.target = target;
	}

	protected Activity copyBaseOf(final Activity source) throws IOException {
		
		final Activity copy;
		
		if(target == Activity.EMPTY) {
			Activities activities = new PgActivities(user);
			copy = activities.add(source.name(), source.description());
			Periodicity periodicity = source.periodicity();
			copy.periodicity(periodicity.number(), periodicity.unit(), periodicity.reference(), periodicity.closeInterval());
		} else {
			copy = target;
		}
		
		return copy;
	}

	protected abstract DataModel copyDataModel(final Activity copy, final DataModel source) throws IOException;
	protected abstract DataModel copyDataModel(final DataModel source, final DataModel target) throws IOException;
	protected abstract void copyTableDataField(DataSheetModel targetModel, TableDataField source) throws IOException;
	protected abstract void copyEditableDataField(DataSheetModel targetModel, EditableDataField source) throws IOException;
	protected abstract void copyFormularDataField(AggregatedModel targetModel, FormularDataField source) throws IOException;
	protected abstract void copyParamDataField(AggregatedModel targetModel, ParamDataField source) throws IOException;
	protected abstract void copyInteractingDataModels(final Activity copy) throws IOException;
	protected abstract ModelFilter copyModelFilter(final AggregatedModel copy, final ModelFilter source) throws IOException;
	
	protected void copyModelFiltersTo(final AggregatedModel source, final AggregatedModel copy) throws IOException {
		// enregistrer les filtres
		for (
				ModelFilter filter : source.filters()
										   .where(ModelFilter::model, source.id())
										   .items() // own filters
			) {
			copyModelFilter(copy, filter);			
		}
	}
	
	private void copyDataModelsTo(final Activity copy) throws IOException {
		
		// remove all unused data sheet models
		for (DataSheetModel newModel : copy.forms().items()) {

			if(
					source.forms()
                    	  .where(DataSheetModel::code, newModel.code())
                    	  .isEmpty()
            ) {
				// le modèle est inutilisé, le supprimer (avec ses modèles agrégés)
				copy.forms().remove(newModel);
			}
		}
		
		// remove all unused aggregated models
		for (AggregatedModel newModel : copy.aggregatedModels().items()) {

			if(
					source.aggregatedModels()
                    	  .where(AggregatedModel::code, newModel.code())
                    	  .isEmpty()
            ) {
				// le modèle est inutilisé, le supprimer (avec ses modèles agrégés)
				copy.aggregatedModels().remove(newModel);
			}
		}
				
		// take all source models without structures
		final List<DataModel> sourceModels = new ArrayList<>();	
		final List<DataModel> sourceDataSheetModels = new ArrayList<>();
		List<DataModel> sourceAggregatedModels = new ArrayList<>();
		for (DataModel model : source.dataModels().where(DataModel::interacting, false).items()) { 
			if(new DataSheetModelTableWrappingImpl(model).isStructure())
				continue;
			
			sourceModels.add(model);
			if(model.type() == DataModelType.DATA_SHEET_MODEL)
				sourceDataSheetModels.add(model);
			else 
				sourceAggregatedModels.add(model);
		}
				
		// create all target data sheet models
		for(DataModel model : sourceDataSheetModels) {
			final DataModel newModel;
			if(
					copy.dataModels()
        	  		    .where(DataModel::code, model.code())
        	  		    .any()
	        ) { 
				// model exists. take it only;
				newModel = copy.dataModels()
							   .where(DataModel::code, model.code())
							   .first();
				
				copyDataModel(model, newModel);
			} else {
				// new model. we copy only model without its content.
				newModel = copyDataModel(copy, model);
			}
			
			dataModelMappings.put(model.id(), newModel);
			
			// create its tables without content
			for (TableDataField table : model.fields().tables().items()) {
				final DataSheetModel newDModel = (DataSheetModel)newModel;
				copyTableDataField(newDModel, table);
				sourceModels.add(table.structure());
			}
		}
		
		// create all target aggregated models
		// sort aggregated models on dependency
		sourceAggregatedModels = new DataModelSortingOnDependency(sourceAggregatedModels, true).items();
		for(DataModel model : sourceAggregatedModels) {
			final DataModel newModel;
			if(
					copy.dataModels()
        	  		    .where(DataModel::code, model.code())
        	  		    .any()
	        ) { 
				// model exists. take it only;
				newModel = copy.dataModels()
							   .where(DataModel::code, model.code())
							   .first();
				
				copyDataModel(model, newModel);
			} else {
				// new model. we copy only model without its content.
				newModel = copyDataModel(copy, model);
			}
			
			dataModelMappings.put(model.id(), newModel);
		}
		
		copyDataModelContent(sourceModels, copy, Activity.EMPTY);		
	}
	
	protected abstract DataModel targetModelOf(final DataModel sourceModel, final Activity copy, final Activity interactingActivity) throws IOException;
	
	protected void copyDataModelContent(final List<DataModel> sourceModels, final Activity copy, final Activity actor) throws IOException {
		
		List<DataField> sourceFields = new ArrayList<>();
		List<DataField> targetFields = new ArrayList<>();
		
		for (DataModel model : sourceModels) {
			
			final DataModel newModel = targetModelOf(model, copy, actor); 
			
			if(model.type() == DataModelType.DATA_SHEET_MODEL) {
				// remove all unused fields
				for (DataField newField : newModel.fields().items()) {
					if(
							model.fields()
		                    	 .where(DataField::code, newField.code())
		                    	 .isEmpty()
		            ) {
						// model is unused, remove it
						newModel.fields().remove(newField);
					}
				}
				
				for (EditableDataField field : model.fields().scalarEditableFields().items()) {
					if(field.userScope() == UserScope.USER || field.code().equals("PARENT")) {
						sourceFields.add(field);
					}			
				}
				
				for (EditableDataField field : newModel.fields().scalarEditableFields().items()) {
					if(field.userScope() == UserScope.USER || field.code().equals("PARENT")) {
						targetFields.add(field);
					}			
				}
			} else {
				// remove all unused parameters
				for (ParamDataField newParam : newModel.fields().params().where(ParamDataField::model, model.id()).items()) {
					if(
							model.fields()
							     .params()
								 .where(ParamDataField::code, newParam.code())
								 .where(ParamDataField::model, model.id()) 
								 .isEmpty()
					  ) {
						newModel.fields().params().remove(newParam);
					}
				}
				
				sourceFields.addAll(
						model.fields()
							 .where(
								new PgFilter<>(DataField.class, ConditionOperator.OR)
									.add(DataField::style, Matchers.equalsTo(DataFieldStyle.PARAMETER))
								    .add(DataField::style, Matchers.equalsTo(DataFieldStyle.FORMULAR))
							 )
							 .where(DataField::model, model.id()) // own data
							 .items()
				);		
				
				final AggregatedModel newAModel = (AggregatedModel)newModel;
				newAModel.filters().remove();
				newAModel.formulars().remove();
			}
		}
		
		// create or update fields
		for (DataField field : new DataFieldSortingOnDependency(sourceFields).items()) {			
			final DataModel targetModel = targetModelOf(field.model(), copy, actor);			
			switch (field.style()) {
				case SIMPLE:
				case LIST:
					final EditableDataField editable = (EditableDataField)field;
					copyEditableDataField((DataSheetModel)targetModel, editable);
					break;
				case FORMULAR:
					final FormularDataField formular = (FormularDataField)field;
					copyFormularDataField((AggregatedModel)targetModel, formular);
					break;			
				case PARAMETER:
					final ParamDataField parameter = (ParamDataField)field;
					copyParamDataField((AggregatedModel)targetModel, parameter);
					break;
				default:
					throw new IllegalArgumentException(String.format("Copying data fields : style %s not supported !", field.style().toString()));
			}	
		}
					
		for (DataModel model : sourceModels) {
			if(model.type() == DataModelType.AGGREGATED_MODEL) {
				final AggregatedModel sourceModel = (AggregatedModel)model;
				final AggregatedModel targetModel = (AggregatedModel)targetModelOf(sourceModel, copy, actor);
				
				// set date reference of aggregated model
				targetModel.update(targetModel.name(), targetModel.fields().get(sourceModel.dateReference().code()));
				
				// create filters
				copyModelFiltersTo(sourceModel, targetModel);
			}			
		}
	}
	
	protected abstract Indicator copyIndicator(final Activity copy, final Indicator source) throws IOException;
	
	protected abstract void copyIndicator(final Indicator source, final Indicator target) throws IOException;
	
	private void copyIndicatorsTo(final Activity copy) throws IOException {
		
		// mettre à jour les indicateurs de l'activité
		// - supprimer les indicateurs inutilisés
		for (Indicator newIndicator : copy.indicators().items()) {
			
			if(
				source.indicators()
				      .where(Indicator::code, newIndicator.code())
				      .isEmpty()
			) {
				copy.indicators().remove(newIndicator);
			}				
		}
		
		// - mettre à jour ou ajouter les nouveaux indicateurs
		for (Indicator actIndic : source.indicators().items()) {
			
			final Indicator newActIndic;

			if(
				copy.indicators()
				    .where(Indicator::code, actIndic.code())
				    .isEmpty()
			) {
				newActIndic = copyIndicator(copy, actIndic);
			} else {
				newActIndic = copy.indicators() 
			                      .where(Indicator::code, actIndic.code())
			                      .first();	
				
				copyIndicator(actIndic, newActIndic);
			}		
			
			newActIndic.locate(actIndic.sizeX(), actIndic.sizeY(), actIndic.row(), actIndic.col());		
		}
	}
	
	protected void copyActivityParamsTo(final Activity copy) throws IOException {
		
		// enregistrer les paramètres de l'activité
		// il n y a que les paramètres existants dans les modèles
		for (ActivityParam aParam : source.params().items()) {
			
			AggregatedModel newAModel = (AggregatedModel)dataModelMappings.get(aParam.model().id());
			
			ActivityParams newParams = copy.params()
					                       .where(ActivityParam::code, aParam.code())
					                       .where(ActivityParam::model, newAModel.id()); 			
			
			if(newParams.isEmpty()) {
				ParamDataField param = newAModel.params()
						                   .where(ParamDataField::code, aParam.code())
						                   .first();
				
				newParams.put(param, aParam.value());
			}
		}
	}

	protected abstract DataSheet copyDataSheet(final DataSheetModel copy, final DataSheet source) throws IOException;
	
	protected void copyDataSheetsTo(final Activity copy) throws IOException {
		for (DataSheetModel form : source.primaryForms()) {
			for (DataSheet sheet : form.sheets().items()) {
				final DataSheetModel newForm = (DataSheetModel)dataModelMappings.get(form.id());
				copyDataSheet(newForm, sheet);
			}
		}
	}
	
	@Override
	public Activity copy() throws IOException {
		
		final Activity copy = copyBaseOf(source);	
		copyDataModelsTo(copy);
		copyInteractingDataModels(copy);
		copyIndicatorsTo(copy);
		copyActivityParamsTo(copy);
		copyDataSheetsTo(copy);
		return copy;
	}

}
