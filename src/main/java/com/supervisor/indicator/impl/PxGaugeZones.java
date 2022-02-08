package com.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.supervisor.sdk.colors.Color;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.indicator.Gauge;
import com.supervisor.indicator.GaugeZone;
import com.supervisor.indicator.GaugeZones;

public final class PxGaugeZones extends DomainRecordables<GaugeZone, GaugeZones> implements GaugeZones {

	private final Gauge gauge;
	
	public PxGaugeZones(final RecordSet<GaugeZone> source, final Gauge gauge) throws IOException {
		super(PxGaugeZone.class, source);
		
		this.gauge = gauge;
		this.source = source.where(GaugeZone::gauge, gauge.id());
	}
	
	@Override
	protected GaugeZones domainSetOf(final RecordSet<GaugeZone> source) throws IOException {
		return new PxGaugeZones(source, gauge);
	}
	
	@Override
	public List<GaugeZone> items() throws IOException {
		List<GaugeZone> items = super.items();
		items.sort((c1, c2) -> {
			try {
				return Double.compare(c1.min(), c2.min());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		return items;           
	}

	@Override
	public GaugeZone add(Color color, double min, double max) throws IOException {
		
		source.mustCheckThisCondition(
				color != Color.NONE, 
				"Vous devez spécifier une couleur !"
		);
		
		source.mustCheckThisCondition(
				min < max, 
				"Le minimum doit être inférieur au maximum !"
		);
		
		source.mustCheckThisCondition(
				min >= gauge.min(), 
				"Le minimum doit être supérieur ou égal à la valeur minimale !"
		);
		
		source.mustCheckThisCondition(
				max <= gauge.max(), 
				"Le maximal doit être inférieur ou égal à la valeur maximale !"
		);
		
		Record<GaugeZone> record = source.entryOf(GaugeZone::gauge, gauge.id())
										  .entryOf(GaugeZone::color, color)
									      .entryOf(GaugeZone::min, min)
										  .entryOf(GaugeZone::max, max)
										  .add();
		
		return domainOf(record);
		
	}
}
