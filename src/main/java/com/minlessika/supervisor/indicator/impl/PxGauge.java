package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.minlessika.supervisor.domain.bi.impl.QueryScalarIndicator;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.GaugeType;
import com.minlessika.supervisor.indicator.GaugeZone;
import com.minlessika.supervisor.indicator.GaugeZones;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.indicator.SymbolPosition;

public final class PxGauge extends IndicatorWrap implements Gauge {

	private double number = 0;
	private final Record<Gauge> record;
	
	public PxGauge(final Record<Gauge> record) throws IOException {
		this(
				new IndicatorBase(
						record.listOf(Indicator.class)
						      .get(record.id())
				)
		);	 
	}
	
	public PxGauge(final Indicator origin) throws IOException {
		super(origin);	
		
		this.record = origin.listOf(Gauge.class)
                			.get(origin.id());
	}
	
	@Override
	public void update(String code, String singleLabel, String pluralLabel, String description) throws IOException {
		throw new UnsupportedOperationException("Gauge:update#Indicator");
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		
		final BasePeriodicity periodicity = periodicity(activity);
		
		// calculer le nombre
		final BiPeriod period = new BiPeriodOfPeriodicity(date, periodicity);
		
		number = number(period);
	}
	
	private double number(final BiPeriod period) throws IOException {
		return (Double)
			new QueryScalarIndicator(
		this,
				dynamicParams().first(),
				period
			).result();
	}

	@Override
	public String unitySymbol() throws IOException {
		return record.valueOf(Gauge::unitySymbol);
	}

	@Override
	public SymbolPosition symbolPosition() throws IOException {
		return record.valueOf(Gauge::symbolPosition);
	}

	@Override
	public GaugeZones zones() throws IOException {
		return new PxGaugeZones(
					record.listOf(GaugeZone.class), 
					this
		);
	}

	@Override
	public void update(
			String code, 
			GaugeType gaugeType,
			String unitySymbol, 
			SymbolPosition symbolPosition, 
			String label,
			String description
	) throws IOException {

		record.mustCheckThisCondition(
				gaugeType != GaugeType.NONE, 
				"Vous devez spécifier un type de Jauge !"
		);
		
		origin.update(code, label, label, description);
		
		record.entryOf(Gauge::unitySymbol, unitySymbol)
			  .entryOf(Gauge::symbolPosition, symbolPosition)
			  .entryOf(Gauge::gaugeType, gaugeType)
			  .update();
		
	}

	@Override
	public String label() throws IOException {
		return singleLabel();
	}

	@Override
	public double number() throws IOException {
		return number;
	}

	@Override
	public double min() throws IOException {
		return record.valueOf(Gauge::min); 
	}

	@Override
	public double max() throws IOException {
		return record.valueOf(Gauge::max); 
	}

	@Override
	public GaugeType gaugeType() throws IOException {
		return record.valueOf(Gauge::gaugeType);
	}

	@Override
	public int minorTicks() throws IOException {
		return record.valueOf(Gauge::minorTicks);
	}

	@Override
	public int majorTicks() throws IOException {
		return record.valueOf(Gauge::majorTicks);
	}

	@Override
	public void graduate(int minor, int major) throws IOException {
		
		record.mustCheckThisCondition(
				minor > 0, 
				"La division mineure doit être supérieure à 0 !"
		);
		
		record.mustCheckThisCondition(
				major > 0, 
				"La division majeure doit être supérieure à 0 !"
		);
		
		record.entryOf(Gauge::minorTicks, minor)
			  .entryOf(Gauge::majorTicks, major)
			  .update();
	}

	@Override
	public List<Double> graduations() throws IOException {
		List<Double> graduations = new ArrayList<>();
		Double min = min();
		Double max = max();
		
		graduations.add(min);
		
		Double step = (max - min) / majorTicks();
		Double nextTick = min + step;
		while (nextTick < max) {
			graduations.add(nextTick);
			nextTick += step;
		}
		
		graduations.add(max);
		
		return graduations;
	}

	@Override
	public void limit(double min, double max) throws IOException {
		record.mustCheckThisCondition(
				min < max, 
				"La borne inférieure doit être inférieure à la borne supérieure !"
		);
		
		record.entryOf(Gauge::min, min)
			  .entryOf(Gauge::max, max)
			  .update();
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		
		Indicators indicators = activity.indicators();
		Gauge newIndicator = (Gauge)indicators.add(code(), IndicatorType.GAUGE);
		newIndicator.update(
				newIndicator.code(), 
				gaugeType(), 
				unitySymbol(), 
				symbolPosition(), 
				label(), 
				description()
		);
		newIndicator.graduate(minorTicks(), majorTicks());
		newIndicator.limit(min(), max());
		
		for (GaugeZone zone : zones().items()) {
			newIndicator.zones().add(zone.color(), zone.min(), zone.max());
		}
		
		return newIndicator;
	}

	@Override
	public void copy(Indicator source) throws IOException {
		
		Gauge copy = (Gauge)source;
		
		update(
			code(), 
			copy.gaugeType(), 
			copy.unitySymbol(), 
			copy.symbolPosition(), 
			copy.label(), 
			copy.description()
		);
	}
}
