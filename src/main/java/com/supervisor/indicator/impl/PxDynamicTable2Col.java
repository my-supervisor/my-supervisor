package com.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.supervisor.domain.bi.impl.PreviousBiPeriod;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.pgsql.PgSimpleOrdering;
import com.supervisor.sdk.time.PeriodicityUnit;
import org.apache.commons.lang.StringUtils;
import com.supervisor.domain.Activity;
import com.supervisor.domain.bi.impl.QueryFigureListIndicator;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.DynamicTable2Col;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.Indicators;
import com.supervisor.indicator.OrderingMode;
import com.supervisor.indicator.SymbolPosition;

public final class PxDynamicTable2Col extends IndicatorWrap implements DynamicTable2Col  {

	private List<Double> increaseRates;
	private List<Integer> increaseInPercents;
	private List<String> names;
	private List<Double> values;
	private final Record<DynamicTable2Col> record;
	
	public PxDynamicTable2Col(final Indicator origin) throws IOException {
		super(origin);
		
		this.record = origin.listOf(DynamicTable2Col.class)
	                        .get(origin.id());
		
		this.names = new ArrayList<>();
		this.values = new ArrayList<>();
		this.increaseRates = new ArrayList<>();
		this.increaseInPercents = new ArrayList<>();
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		
		BasePeriodicity periodicity = periodicity(activity);
		BiPeriod period = new BiPeriodOfPeriodicity(date, periodicity);
		
		final Ordering ordering;
		switch (orderingMode()) {
			case ASC:
				ordering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.ASC, Arrays.asList("\"value\""));
				break;
			case DESC:
				ordering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.DESC, Arrays.asList("\"value\""));
				break;
			default:
				ordering = Ordering.EMPTY;
				break;
		}
		
		final int limit = nbMaxOfElementsToShow();
		final IndicatorDynamicParam entityParam = dynamicParams().get("ENTITY");
		final IndicatorDynamicParam valueParam = dynamicParams().get("QTE");		
		
		Map<String, Double> results = new QueryFigureListIndicator(this, entityParam, valueParam, period, limit, ordering).figures();
		
		names = results.keySet()
				       .stream()
				       .collect(Collectors.toList());
		
		values = results.values()
				        .stream()
				        .collect(Collectors.toList());
		
		// calculer le taux d'évolution
		increaseRates = new ArrayList<>(values.size());
		increaseInPercents = new ArrayList<>(values.size());
		
		if(manageEvolutionPercent() && periodicity.unit() != PeriodicityUnit.FOREVER) {
			
			final BiPeriod previousPeriod = new PreviousBiPeriod(period, periodicity);
			Map<String, Double> previousResults = new QueryFigureListIndicator(this, entityParam, valueParam, previousPeriod, limit, ordering).figures();
			
			for (Map.Entry<String, Double> post : results.entrySet()) {
				if(previousResults.containsKey(post.getKey())) {
					double previousNumber = previousResults.get(post.getKey());
					if(previousNumber == 0) {
						increaseRates.add(-777.0);
						increaseInPercents.add(-77700);
					}else {
						double number = post.getValue();					
						double increaseRate = Indicator.roundNumber((number - previousNumber) / previousNumber, 2);
						increaseRates.add(increaseRate);
						increaseInPercents.add((int)(increaseRate * 100));
					}					
				}else {
					increaseRates.add(-777.0);
					increaseInPercents.add(-77700);
				}
			}	
		}		
	}

	@Override
	public String label() throws IOException {
		return singleLabel();
	}

	@Override
	public boolean manageEvolutionPercent() throws IOException {
		return record.valueOf(DynamicTable2Col::manageEvolutionPercent);
	}

	@Override
	public String unitySymbol() throws IOException {
		return record.valueOf(DynamicTable2Col::unitySymbol);
	}

	@Override
	public SymbolPosition symbolPosition() throws IOException {
		return record.valueOf(DynamicTable2Col::symbolPosition);
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
			String label, 
			boolean manageEvolutionPercent, 
			String unitySymbol,
			SymbolPosition symbolPosition, 
			OrderingMode orderingMode,
			int nbMaxOfElementsToShow,
			String description
	) throws IOException {
		
		origin.update(code, label, label, description);
		
		record.entryOf(DynamicTable2Col::manageEvolutionPercent, manageEvolutionPercent)
			  .entryOf(DynamicTable2Col::unitySymbol, unitySymbol)
			  .entryOf(DynamicTable2Col::symbolPosition, symbolPosition)
			  .entryOf(DynamicTable2Col::orderingMode, orderingMode)
			  .entryOf(DynamicTable2Col::nbMaxOfElementsToShow, nbMaxOfElementsToShow)
			  .update();
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
	public OrderingMode orderingMode() throws IOException {
		return record.valueOf(DynamicTable2Col::orderingMode);
	}

	@Override
	public int nbMaxOfElementsToShow() throws IOException {
		return record.valueOf(DynamicTable2Col::nbMaxOfElementsToShow);
	}
	
	@Override
	public List<Double> increaseRates() throws IOException {
		return increaseRates;
	}

	@Override
	public List<Integer> increaseInPercents() throws IOException {
		return increaseInPercents;
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		
		Indicators indicators = activity.indicators();
		DynamicTable2Col newIndicator = (DynamicTable2Col)indicators.add(code(), IndicatorType.DYNAMIC_TABLE_2_COL);
		
		newIndicator.update(
			newIndicator.code(), 
			label(), 
			manageEvolutionPercent(), 
			unitySymbol(), 
			symbolPosition(), 
			orderingMode(), 
			nbMaxOfElementsToShow(), 
			description()
		);
		
		return newIndicator;
	}

	@Override
	public void copy(Indicator source) throws IOException {
		DynamicTable2Col copy = (DynamicTable2Col)source;
		update(
			code(),
			copy.label(), 
			copy.manageEvolutionPercent(), 
			copy.unitySymbol(), 
			copy.symbolPosition(), 
			copy.orderingMode(), 
			copy.nbMaxOfElementsToShow(), 
			copy.description()
		);
	}
}
