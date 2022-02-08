package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityParam;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserAggregatedModels;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivitySave extends TkBaseWrap {

	public TkActivitySave(Base base) {
		super(
				base,
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final Activities myActivities = module.activities();
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));							
					final String name = form.single("name");
					final String description = form.single("description");
					final PeriodicityUnit periodicityUnit = PeriodicityUnit.valueOf(form.single("periodicity_unit_id"));
					final int periodicityNumber = Integer.parseInt(form.single("periodicity_number"));
					final LocalDate periodicityReference = LocalDate.parse(form.single("periodicity_reference"), DateTimeFormatter.ISO_LOCAL_DATE);
					final boolean periodicityCloseInterval = Boolean.parseBoolean(form.single("periodicity_close_interval"));
					
					Activity activity;	
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						if(myActivities.contains(id.value()))
							activity = myActivities.get(id.value());
						else
							activity = module.activityTemplates().get(id.value());
						
						if(new RqUser(base, req).notOwn(activity)) {
							throw new IllegalArgumentException("Vous ne pouvez pas modifier une activité partagée !");
						}
						
						activity.update(name, description);
					}else
					{
						activity = myActivities.add(name, description);
					}
					
					activity.periodicity(periodicityNumber, periodicityUnit, periodicityReference, periodicityCloseInterval);
					
					// enregistrer les paramètres		
					final UserAggregatedModels models = module.aggregatedModels();
					final int nbOfParamsToTreat = getValuesOfRow("param_index", form).size();
					for (int i = 0; i < nbOfParamsToTreat; i++) {			
						
						String state = getRowValueAt("param_state", form, i);
						
						if(state.equals("added")) {
							UUID modelId = UUID.fromString(getValuesOfRow("param_model_id", form).get(i));
							UUID paramId = UUID.fromString(getValuesOfRow("param_base_param_id", form).get(i));
							ParamDataField param = models.get(modelId).params().get(paramId);
							
							String value = getValuesOfRow("param_value", form).get(i);
							activity.params().put(param, value);				
						}
						
						if(state.equals("modified")) {
							UUID paramId = UUID.fromString(getValuesOfRow("param_id", form).get(i));
							ActivityParam param = activity.params().get(paramId);
							String value = getValuesOfRow("param_value", form).get(i);
							param.update(value);
						}
						
						if(state.equals("removed")) {
							UUID paramId = UUID.fromString(getValuesOfRow("param_id", form).get(i));
							activity.params().remove(paramId);
						}					
					}
							
					final String msg;
					if(id.isPresent())
						msg = String.format("L'activité %s a été modifiée avec succès !", activity.name());
					else
						msg = String.format("L'activité %s a été créée avec succès !", activity.name());
					
					if(activity.isTemplate()) {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            "/activity/template"
						    );
					}else {
						return new RsForward(
								new RsFlash(
					                msg,
					                Level.FINE
					            ),
					            "/home?activity=" + activity.id()
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
}
