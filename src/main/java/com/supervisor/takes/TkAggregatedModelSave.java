package com.supervisor.takes;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataFields;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.AggregatedModels;
import com.supervisor.domain.DataField;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ModelFilterCondition;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularDataFields;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkAggregatedModelSave extends TkBaseWrap {

	public TkAggregatedModelSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));						
					
					AggregatedModel itemSaved = null;
					
					final UUID activityId = UUID.fromString(form.single("activity_id"));
					final Activity activity = module.activities().get(activityId);
					final AggregatedModels items = activity.aggregatedModels();
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						final UUID dateReferenceId = UUID.fromString(form.single("date_reference_id"));
						
						itemSaved = items.get(id.value());
						final DataField dateReference = itemSaved.fields().get(dateReferenceId);
						DataFields fields = itemSaved.model().fields();
						
						int nbOfParamsToTreat = getValuesOfRow("param_index", form).size();
						for (int i = 0; i < nbOfParamsToTreat; i++) {
							String state = getRowValueAt("param_state", form, i);
							
							if(state.equals("added")) {
								String name = getRowValueAt("param_name", form, i);
								String value = getRowValueAt("param_value", form, i);
								DataFieldType type = DataFieldType.valueOf(getRowValueAt("param_type", form, i));
								
								final CodeGenerator generator = new BasicCodeGenerator(
																	itemSaved.fields(), 
																	name
																);
								
								itemSaved.params()
								         .add(generator.generate(), name, value, type);
							}
							
							if(state.equals("modified")) {
								UUID paramId = UUID.fromString(getRowValueAt("param_id", form, i));
								ParamDataField param = itemSaved.params()
										                     .get(paramId);
								
								String name = getRowValueAt("param_name", form, i);
								DataFieldType type = DataFieldType.valueOf(getRowValueAt("param_type", form, i));
								
								param.update(param.code(), name, type); 
																
								String value = getRowValueAt("param_value", form, i);
								param.update(value); 							
							}
							
							if(state.equals("removed")) {
								UUID paramId = UUID.fromString(getRowValueAt("param_id", form, i));
								itemSaved.params()
										 .remove(paramId);
							}
						}
						
						int nbOfFiltersToTreat = getValuesOfRow("filter_index", form).size();
						for (int i = 0; i < nbOfFiltersToTreat; i++) {
							String state = getRowValueAt("filter_state", form, i);
							long index = getRowLongValueAt("filter_index", form, i);
							
							if(state.equals("added")) {
								ModelFilter filter = itemSaved.filters().add();
								
								saveConditionsOf(filter, form, (int)index, fields);
							}
							
							if(state.equals("modified")) {
								UUID filterId = UUID.fromString(getRowValueAt("filter_id", form, i));
								ModelFilter filter = itemSaved.filters().get(filterId);
								
								saveConditionsOf(filter, form, (int)index, fields);
							}
							
							if(state.equals("removed")) {
								UUID filterId = UUID.fromString(getRowValueAt("filter_id", form, i));
								itemSaved.filters().remove(filterId);
							}
						}
						
						final String name = form.single("name");
						itemSaved.update(name, dateReference); 
						
						return new RsForward(
								new RsFlash(
					                "Le modèle a été modifié avec succès !",
					                Level.FINE
					            ),
								"/collect/aggregated-model"
						    );
					} else {
						final String name = form.single("name");
						final CodeGenerator generator = new BasicCodeGenerator(
															activity.dataModels(), 
															name
														);
						
						final UUID modelId = UUID.fromString(form.single("model_id"));
						final DataModel model = module.dataModels().get(modelId);
						itemSaved = items.add(generator.generate(), model);						
						itemSaved.update(name, model.fields().get("DATE"));  
						
						return new RsForward(
							new RsFlash(
				                "Le modèle a été créé avec succès !",
				                Level.FINE
				            ),
							String.format("/collect/aggregated-model/edit?id=%s", itemSaved.id())
					    );
					}
				}
		);
	}

	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList(form.param(name).iterator());
	}
	
	private static String getRowValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return getValuesOfRow(name, form).get(index);
	}
	
	private static Long getRowLongValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return Long.parseLong(getRowValueAt(name, form, index));
	}
	
	private static void saveConditionsOf(final ModelFilter filter, final RqFormSmart form, final int index, final DataFields fields) throws IOException {
		
		FormularDataFields formulars = filter.model().formulars();
		
		for (int i = 0; i < getValuesOfRow("cond_filter_index", form).size(); i++) {
			
			Long filterIndex = getRowLongValueAt("cond_filter_index", form, i);
			if(filterIndex != index)
				continue;
			
			String state = getRowValueAt("cond_state", form, i);
			
			if(state.equals("added")) {
				String code = getValuesOfRow("cond_field_code", form).get(i);
				Comparator comparator = Comparator.valueOf(getRowValueAt("cond_comparator_id", form, i));
				String value = getRowValueAt("cond_value", form, i);
				
				if(fields.where(DataField::code, code).any()) {
					DataField field = fields.where(DataField::code, code).first();
					filter.conditions()
					      .add(field, comparator, value);
				}else {
					FormularDataField formular = formulars.where(FormularDataField::code, code).first();
					filter.conditions()
				          .add(formular, comparator, value);
				}
				
			}
			
			if(state.equals("modified")) {
							
				String code = getValuesOfRow("cond_field_code", form).get(i);
				
				Comparator comparator = Comparator.valueOf(getRowValueAt("cond_comparator_id", form, i));
				String value = getRowValueAt("cond_value", form, i);
				
				UUID id = UUID.fromString(getRowValueAt("cond_id", form, i));
				ModelFilterCondition cond = filter.conditions()
							                     .get(id);
				
				if(fields.where(DataField::code, code).any()) {
					DataField field = fields.where(DataField::code, code).first();
					cond.update(field, comparator, value);
				}else {
					FormularDataField formular = formulars.where(FormularDataField::code, code).first();
					cond.update(formular, comparator, value);
				}				
			}
			
			if(state.equals("removed")) {
				UUID id = UUID.fromString(getRowValueAt("cond_id", form, i));
				filter.conditions()
					  .remove(id);
			}
		}
	}	
}
