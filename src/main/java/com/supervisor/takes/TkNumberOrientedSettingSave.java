package com.supervisor.takes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.IndicatorTemplate;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.NumberOriented;
import com.supervisor.indicator.SymbolPosition;
import com.supervisor.indicator.impl.PxIndicatorTemplate;

public final class TkNumberOrientedSettingSave extends TkBaseWrap {

	public TkNumberOrientedSettingSave(final Base base) {
		super(
				base,
				req -> {
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Supervisor module = new PxSupervisor(base, req);
					Activities myActivities = module.activities();
					
					final String singleLabel = form.single("single_label");
					final String pluralLabel = form.single("plural_label");
					final String description = form.single("description");
					final String unitySymbol = form.single("unity_symbol", "");
					final SymbolPosition symbolPosition = SymbolPosition.valueOf(form.single("symbol_position", "RIGHT"));
					final boolean manageEvolutionPercent = Boolean.parseBoolean(form.single("manage_evolution_percent"));		
					final UUID activityId = UUID.fromString(form.single("activity_id"));
					final Activity activity = myActivities.get(activityId);
					
					if(new RqUser(base, req).notOwn(activity)) {
						throw new IllegalArgumentException("Vous ne pouvez pas mettre à jour l'indicateur d'une activité partagée !");
					}
					
					NumberOriented itemSaved;

					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						itemSaved = (NumberOriented)activity.indicators().get(id.get());
									
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
															singleLabel
														);
						
						itemSaved = (NumberOriented)activity.indicators().add(generator.generate(), IndicatorType.NUMBER_ORIENTED);
					}
					
					itemSaved.update(itemSaved.code(), singleLabel, pluralLabel, manageEvolutionPercent, unitySymbol, symbolPosition, description);
					
					if(itemSaved.isTemplate()) {
						final List<String> tags = Arrays.asList(StringUtils.split(form.single("tags"), ','));
						final String name = form.single("name");
						
						IndicatorTemplate template = new PxIndicatorTemplate(itemSaved);
						template.update(name, description);
						template.tags(tags);
					}
					
					final String msg;
					
					if(id.isPresent())
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
