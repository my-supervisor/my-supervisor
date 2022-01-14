package com.minlessika.supervisor.indicator.impl;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.minlessika.supervisor.domain.bi.impl.QueryFigureListIndicator;
import com.minlessika.supervisor.indicator.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PxChartCamembert extends IndicatorWrap implements ChartCamembert {

	private List<String> columns;
	private List<String> names;
	private List<Double> values;
	private final Record<ChartCamembert> record;
	
	public PxChartCamembert(final Indicator origin) throws IOException {
		super(origin);
		
		this.record = origin.listOf(ChartCamembert.class)
				            .get(origin.id());
		
		this.columns = new ArrayList<>();
		this.names = new ArrayList<>();
		this.values = new ArrayList<>();
	}

	@Override
	public ChartCamembertType camembertType() throws IOException {
		return record.valueOf(ChartCamembert::camembertType);
	}

	@Override
	public String label() throws IOException {
		return singleLabel();
	}
	
	@Override
	public List<String> columns() throws IOException {
		return columns;		
	}

	@Override
	public List<String> names() throws IOException {
		return names;
	}
	
	@Override
	public List<Double> values() throws IOException {
		return values;
	}
	
	@Override
	public void update(
			String code, 
			String singleLabel, 
			String pluralLabel, 
			String description
	) throws IOException {
		throw new UnsupportedOperationException("ChartCamembert:update#Indicator");
	}

	@Override
	public void update(
			String code, 
			ChartCamembertType camembertType, 
			String label, 
			String subLabel, 
			String description
	) throws IOException {
		
		record.mustCheckThisCondition(
				camembertType != ChartCamembertType.NONE, 
				"Vous devez spécifier un type de camembert !"
		);
		
		origin.update(code, label, label, description);
		
		record.entryOf(ChartCamembert::camembertType, camembertType)
			  .entryOf(ChartCamembert::subLabel, subLabel)
			  .update();
	}	

	@Override
	public String subLabel() throws IOException {
		return record.valueOf(ChartCamembert::subLabel);
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {

		final BasePeriodicity periodicity = periodicity(activity);
		final BiPeriod period = new BiPeriodOfPeriodicity(date, periodicity);
		final IndicatorDynamicParam entityParam = dynamicParams().get("ENTITY");
		final IndicatorDynamicParam valueParam = dynamicParams().get("QTE");
		
		Map<String, Double> results = new QueryFigureListIndicator(this, entityParam, valueParam, period).figures();
		
		names = results.keySet()
				       .stream()
				       .collect(Collectors.toList());
		
		values = results.values()
				        .stream()
				        .collect(Collectors.toList());
		
		columns = new ArrayList<>();
		for (int i = 1; i <= names.size(); i++) {
			columns.add(String.format("data%s", i)); 
		}
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		
		Indicators indicators = activity.indicators();
		ChartCamembert newIndicator = (ChartCamembert)indicators.add(code(), IndicatorType.CHART_CAMEMBERT);
		
		newIndicator.update(
				newIndicator.code(), 
				camembertType(), 
				label(), 
				subLabel(), 
				description()
		);
		
		return newIndicator;
	}

	@Override
	public void copy(Indicator source) throws IOException {
		ChartCamembert copy = (ChartCamembert)source;
		update(
			code(), 
			copy.camembertType(), 
			copy.label(), 
			copy.subLabel(), 
			copy.description()
		);
	}
}
