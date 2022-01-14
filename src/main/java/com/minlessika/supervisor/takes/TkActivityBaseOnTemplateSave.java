package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplateParamRequest;
import com.minlessika.supervisor.domain.ActivityTemplateRequest;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.impl.ActivityTemplateParamRequestImpl;
import com.minlessika.supervisor.indicator.impl.ActivityTemplateRequestImpl;

public final class TkActivityBaseOnTemplateSave extends TkBaseWrap {

	public TkActivityBaseOnTemplateSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Long id = Long.parseLong(form.single("id"));
					String name = form.single("name");
					final PeriodicityUnit periodicityUnit = PeriodicityUnit.valueOf(form.single("periodicity_unit_id"));
					final int periodicityNumber = Integer.parseInt(form.single("periodicity_number"));
					final LocalDate periodicityReference = LocalDate.parse(form.single("periodicity_reference"), DateTimeFormatter.ISO_LOCAL_DATE);		
					
					ActivityTemplate template = module.activityTemplates().get(id);		
					
					// récupérer les paramètres personnalisés de l'activité
					List<ActivityTemplateParamRequest> params = new ArrayList<>();
					int nbOfParamsToTreat = getValuesOfRow("param_index", form).size();
					for (int i = 0; i < nbOfParamsToTreat; i++) {			
						
						String state = getRowValueAt("param_state", form, i);
						
						if(state.equals("modified")) {
							Long modelId = Long.parseLong(getValuesOfRow("param_model_id", form).get(i));
							String code = getRowValueAt("param_code", form, i);
							String value = getRowValueAt("param_value", form, i);
							params.add(new ActivityTemplateParamRequestImpl(modelId, code, value));
						}				
					}
							
					ActivityTemplateRequest request = new ActivityTemplateRequestImpl(name, params);
					Activity activity = template.generate(request);		
					activity.periodicity(periodicityNumber, periodicityUnit, periodicityReference, template.periodicity().closeInterval());
					
					return new RsForward(
							new RsFlash(
									String.format("L'activité %s a été créée avec succès !", activity.name()),
					                Level.FINE
				            ),
				            "/home?activity=" + activity.id()
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
