package com.supervisor.takes;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataLinkParam;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;

public final class TkDataLinkSave extends TkBaseWrap {

	public TkDataLinkSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final DataDomainDefinition ddf = DataDomainDefinition.valueOf(form.single("data_domain_definition_id")); 					
					final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));
					final String source = new RqHref.Smart(req).single("source");
					final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
					final Activity activity = module.activities().get(activityId);
					Indicator indic = activity.indicators().get(indicId);

					if(new RqUser(base, req).notOwn(indic)) {
						throw new IllegalArgumentException("Vous ne pouvez pas modifier les liaisons d'un indicateur partagé !");
					}
							
					DataLink itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					boolean active = Boolean.parseBoolean(form.single("status_id", "true"));
					String name = form.single("name");
					
					if(id > 0) {
						itemSaved = indic.links().get(id);			
						itemSaved.update(name); 
					} else {
						final Long modelId = Long.parseLong(form.single("model_id"));
						DataModel model = module.dataModels().get(modelId);						
						itemSaved = indic.links().add(name, model);
					}
					
					itemSaved.activate(active);
					itemSaved.update(ddf);
					
					// enregistrer les champs de données
					DataModel model = itemSaved.model();
					int nbOfFieldsToTreat = getValuesOfRow("item_param_id", form).size();
					for (int i = 0; i < nbOfFieldsToTreat; i++) {
						
						Long paramId = Long.parseLong(getValuesOfRow("item_param_id", form).get(i));
						IndicatorDynamicParam param = indic.dynamicParams().get(paramId);
						
						Long fieldToUseId = Long.parseLong(getValuesOfRow("item_field_to_use_id", form).get(i));
						DataField fieldToUse = model.fields().get(fieldToUseId);
						
						final DataLinkOperator operator = DataLinkOperator.valueOf(getValuesOfRow("operator_id", form).get(i));
						
						itemSaved.mappings().put(param, operator, fieldToUse);		
					}		
					
					// enregistrer les paramètres		
					int nbOfParamsToTreat = getValuesOfRow("param_index", form).size();
					for (int i = 0; i < nbOfParamsToTreat; i++) {			
						
						String state = getRowValueAt("param_state", form, i);
						
						if(state.equals("added")) {
							Long paramId = Long.parseLong(getValuesOfRow("param_id", form).get(i));
							ParamDataField param = model.fields().params().get(paramId);
							
							String value = getValuesOfRow("param_value", form).get(i);
							itemSaved.params().put(param, value);				
						}
						
						if(state.equals("modified")) {
							Long paramId = Long.parseLong(getValuesOfRow("param_id", form).get(i));
							DataLinkParam param = itemSaved.params().get(paramId);
							String value = getValuesOfRow("param_value", form).get(i);
							param.update(value);
						}
						
						if(state.equals("removed")) {
							Long paramId = Long.parseLong(getValuesOfRow("param_id", form).get(i));
							itemSaved.params().remove(paramId);
						}					
					}
					
					final String msg;
					if(id > 0)
						msg = "La liaison a été modifiée avec succès !";
					else
						msg = "La liaison a été créée avec succès !";
					
					return new RsForward(
							new RsFlash(
				                msg,
				                Level.FINE
				            ),
							String.format("/%s-setting/edit?id=%s&shortname=%s&source=%s", indic.type().shortName(), indic.id(), indic.type().shortName(), source)					
					    );
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
