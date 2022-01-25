package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import com.supervisor.sdk.colors.Color;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import org.apache.commons.collections.IteratorUtils;
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
import com.supervisor.indicator.Gauge;
import com.supervisor.indicator.GaugeType;
import com.supervisor.indicator.IndicatorTemplate;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.SymbolPosition;
import com.supervisor.indicator.impl.PxIndicatorTemplate;

public final class TkGaugeSettingSave extends TkBaseWrap {

	public TkGaugeSettingSave(final Base base) {
		super(
				base,
				req -> {
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Supervisor module = new PxSupervisor(base, req);
					Activities myActivities = module.activities();
					
					final String label = form.single("label");
					final int majorTicks = Integer.parseInt(form.single("major_ticks"));
					final int minorTicks = Integer.parseInt(form.single("minor_ticks"));
					final double min = Double.parseDouble(form.single("min"));
					final double max = Double.parseDouble(form.single("max"));
					final String description = form.single("description");
					final String unitySymbol = form.single("unity_symbol", "");
					final SymbolPosition symbolPosition = SymbolPosition.valueOf(form.single("symbol_position", "RIGHT"));
					final GaugeType gaugeType = GaugeType.valueOf(form.single("gauge_type_id"));		
					
					final Long activityId = Long.parseLong(form.single("activity_id"));
					final Activity activity = myActivities.get(activityId);
					
					if(new RqUser(base, req).notOwn(activity)) {
						throw new IllegalArgumentException("Vous ne pouvez pas mettre à jour l'indicateur d'une activité partagée !");
					}
					
					Gauge itemSaved;
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					if(id > 0) {			
						itemSaved = (Gauge)activity.indicators().get(id);
									
						final String periodicityState = form.single("periodicity_state", "removed");
						if(periodicityState.equals("added")) {
							final PeriodicityUnit periodicityUnit = PeriodicityUnit.valueOf(form.single("periodicity_unit_id"));
							final int periodicityNumber = Integer.parseInt(form.single("periodicity_number"));
							final LocalDate periodicityReference = LocalDate.parse(form.single("periodicity_reference"), DateTimeFormatter.ISO_LOCAL_DATE);
							final boolean periodicityCloseInterval = Boolean.parseBoolean(form.single("periodicity_close_interval"));
							
							itemSaved.periodicity(periodicityNumber, periodicityUnit, periodicityReference, periodicityCloseInterval);
						} else {
							itemSaved.removePeriodicity();
						}	
					} else {		
						final CodeGenerator generator = new BasicCodeGenerator(
															activity.indicators(), 
															label
														);
						
						itemSaved = (Gauge)activity.indicators().add(generator.generate(), IndicatorType.GAUGE);
					}
					
					itemSaved.update(itemSaved.code(), gaugeType, unitySymbol, symbolPosition, label, description);
					itemSaved.graduate(minorTicks, majorTicks);
					itemSaved.limit(min, max);
					
					// enregistrer les zones
					int nbOfZonesToTreat = getValuesOfRow("zone_id", form).size();
					for (int i = 0; i < nbOfZonesToTreat; i++) {
						
						String editState = getValuesOfRow("zone_edit_state", form).get(i);
						
						if(editState.equals("added")) {
							Color color = Color.valueOf(getValuesOfRow("zone_color_id", form).get(i));
							Double zoneMin = Double.parseDouble(getValuesOfRow("zone_min", form).get(i));
							Double zoneMax = Double.parseDouble(getValuesOfRow("zone_max", form).get(i));
							
							itemSaved.zones().add(color, zoneMin, zoneMax);
						}
						
						if(editState.equals("modified")) {
							
							Long zoneId = Long.parseLong(getValuesOfRow("zone_id", form).get(i));
							Color color = Color.valueOf(getValuesOfRow("zone_color_id", form).get(i));
							Double zoneMin = Double.parseDouble(getValuesOfRow("zone_min", form).get(i));
							Double zoneMax = Double.parseDouble(getValuesOfRow("zone_max", form).get(i));
							
							itemSaved.zones()
							         .get(zoneId)
							         .update(color, zoneMin, zoneMax); 
						}
						
						if(editState.equals("removed")) {
							Long zoneId = Long.parseLong(getValuesOfRow("zone_id", form).get(i));
							
							itemSaved.zones()
							         .remove(zoneId);
						}
					}
					
					if(itemSaved.zones().isEmpty())
						throw new IllegalArgumentException("Vous devez saisir au moins une zone !");
							
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

	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList(form.param(name).iterator());
	}
}
