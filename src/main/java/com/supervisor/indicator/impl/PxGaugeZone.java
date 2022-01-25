package com.supervisor.indicator.impl;

import java.io.IOException;

import com.supervisor.sdk.colors.Color;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.indicator.Gauge;
import com.supervisor.indicator.GaugeZone;

public final class PxGaugeZone extends DomainRecordable<GaugeZone> implements GaugeZone {

	public PxGaugeZone(final Record<GaugeZone> source) throws IOException {
		super(source);
	}

	@Override
	public Gauge gauge() throws IOException {
		Record<Gauge> rec = record.of(GaugeZone::gauge);
		return new PxGauge(rec);
	}

	@Override
	public Color color() throws IOException {
		return record.valueOf(GaugeZone::color);
	}

	@Override
	public double min() throws IOException {
		return record.valueOf(GaugeZone::min);
	}

	@Override
	public double max() throws IOException {
		return record.valueOf(GaugeZone::max);
	}

	@Override
	public void update(final Color color, final double min, final double max) throws IOException {
				
		record.mustCheckThisCondition(
				color != Color.NONE, 
				"Vous devez spécifier une couleur !"
		);
		
		record.mustCheckThisCondition(
				min >= 0, 
				"Vous devez spécifier un minimum positif !"
		);
		
		record.mustCheckThisCondition(
				max >= 0, 
				"Vous devez spécifier un maximum positif !"
		);
		
		record.mustCheckThisCondition(
				min < max, 
				"Le minimum doit être inférieur au maximum !"
		);
		
		record.mustCheckThisCondition(
				min >= gauge().min(), 
				"Le minimum doit être supérieur ou égal à la valeur minimale !"
		);
		
		record.mustCheckThisCondition(
				max <= gauge().max(), 
				"Le maximal doit être inférieur ou égal à la valeur maximale !"
		);
		
		record.entryOf(GaugeZone::color, color)
		      .entryOf(GaugeZone::min, min)
			  .entryOf(GaugeZone::max, max)
			  .update();
	}
}
