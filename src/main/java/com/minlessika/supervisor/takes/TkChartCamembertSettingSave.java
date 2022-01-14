package com.minlessika.supervisor.takes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.sdk.utils.BasicCodeGenerator;
import com.minlessika.sdk.utils.CodeGenerator;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.ChartCamembert;
import com.minlessika.supervisor.indicator.ChartCamembertType;
import com.minlessika.supervisor.indicator.IndicatorTemplate;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.impl.PxIndicatorTemplate;

public final class TkChartCamembertSettingSave extends TkBaseWrap {

	public TkChartCamembertSettingSave(final Base base) {
		super(
				base,
				req -> {
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Supervisor module = new PxSupervisor(base, req);
					Activities myActivities = module.activities();
					
					final ChartCamembertType camembertType = ChartCamembertType.valueOf(form.single("camembert_type_id"));
					final String label = form.single("label");
					final String subLabel = form.single("sub_label", "");
					final String description = form.single("description");	
					final Long activityId = Long.parseLong(form.single("activity_id"));
					Activity activity = myActivities.get(activityId);
					
					if(new RqUser(base, req).notOwn(activity)) {
						throw new IllegalArgumentException("Vous ne pouvez pas mettre à jour l'indicateur d'une activité partagée !");
					}
					
					ChartCamembert itemSaved;
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));		

					if(id > 0) {			
						itemSaved = (ChartCamembert)activity.indicators().get(id);
									
						final String periodicityState = form.single("periodicity_state", "removed");
						if(periodicityState.equals("added")) {
							final PeriodicityUnit periodicityUnit = PeriodicityUnit.valueOf(form.single("periodicity_unit_id"));
							final int periodicityNumber = Integer.parseInt(form.single("periodicity_number"));
							final LocalDate periodicityReference = LocalDate.parse(form.single("periodicity_reference"), DateTimeFormatter.ISO_LOCAL_DATE);
							final boolean periodicityCloseInterval = Boolean.parseBoolean(form.single("periodicity_close_interval"));
							
							itemSaved.periodicity(periodicityNumber, periodicityUnit, periodicityReference, periodicityCloseInterval);
						}else {
							itemSaved.removePeriodicity();
						}		
					} else {	
						final CodeGenerator generator = new BasicCodeGenerator(
															activity.indicators(), 
															label
														);
						
						itemSaved = (ChartCamembert)activity.indicators().add(generator.generate(), IndicatorType.CHART_CAMEMBERT);			
					}
					
					itemSaved.update(itemSaved.code(), camembertType, label, subLabel, description);
					
					if(itemSaved.isTemplate()) {
						final List<String> tags = Arrays.asList(StringUtils.split(form.single("tags"), ','));
						final String name = form.single("name");
						
						IndicatorTemplate template = new PxIndicatorTemplate(itemSaved);
						template.update(name, description);
						template.tags(tags);
					}
					
					final String msg;
					
					if(id > 0)
						msg = String.format("L'indicateur %s a été modifié avec succès !", itemSaved.code());
					else
						msg = String.format("L'indicateur %s a été créé avec succès !", itemSaved.code());
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
						IndicatorSource.url(req)
				    );
				}
		);
	}
}
