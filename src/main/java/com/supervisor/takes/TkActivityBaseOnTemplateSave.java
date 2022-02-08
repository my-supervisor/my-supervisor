package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.time.PeriodicityUnit;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateParamRequest;
import com.supervisor.domain.ActivityTemplateRequest;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.impl.ActivityTemplateParamRequestImpl;
import com.supervisor.indicator.impl.ActivityTemplateRequestImpl;

public final class TkActivityBaseOnTemplateSave extends TkBaseWrap {

	public TkActivityBaseOnTemplateSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final UUID id = UUID.fromString(form.single("id"));
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
							UUID modelId = UUID.fromString(getValuesOfRow("param_model_id", form).get(i));
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
