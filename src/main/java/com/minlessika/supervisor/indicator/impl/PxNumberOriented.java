package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;

import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.minlessika.supervisor.domain.bi.impl.PreviousBiPeriod;
import org.apache.commons.lang.StringUtils;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.time.Periodicity;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.bi.impl.QueryScalarIndicator;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.indicator.NumberOriented;
import com.minlessika.supervisor.indicator.SymbolPosition;

public final class PxNumberOriented extends IndicatorWrap implements NumberOriented {

	private double number = 0;
	private double increaseRate = -777;
	private int increaseInPercent = 0;
	private final Record<NumberOriented> record;
	
	public PxNumberOriented(final Indicator origin) throws IOException {
		super(origin);
		
		this.record = origin.listOf(NumberOriented.class)
                            .get(origin.id());
	}
	
	@Override
	public void update(
			String code, 
			String singleLabel, 
			String pluralLabel, 
			String description
	) throws IOException {
		throw new UnsupportedOperationException("NumberOriented:update#Indicator");
	}
	
	@Override
	public void update(
			String code,
			String singleLabel,
			String pluralLabel,
			boolean manageEvolutionPercent, 
			String unitySymbol, 
			SymbolPosition symbolPosition,
			String description
	) throws IOException {
		
		record.mustCheckThisCondition(
				StringUtils.isBlank(unitySymbol) || StringUtils.isNotBlank(unitySymbol) && symbolPosition != SymbolPosition.NONE, 
				"Vous devez indiquer une position pour le symbole !"
		);
		
		origin.update(code, singleLabel, pluralLabel, description);
		
		record.entryOf(NumberOriented::manageEvolutionPercent, manageEvolutionPercent)
			  .entryOf(NumberOriented::unitySymbol, unitySymbol)
			  .entryOf(NumberOriented::symbolPosition, symbolPosition)
			  .update();
	}

	@Override
	public boolean manageEvolutionPercent() throws IOException {
		return record.valueOf(NumberOriented::manageEvolutionPercent);
	}

	@Override
	public String unitySymbol() throws IOException {
		return record.valueOf(NumberOriented::unitySymbol);
	}

	@Override
	public SymbolPosition symbolPosition() throws IOException {
		return record.valueOf(NumberOriented::symbolPosition);
	}

	@Override
	public String label() throws IOException {
		if(Math.abs(number()) > 1)
			return pluralLabel();
		else
			return singleLabel();
	}
	
	@Override
	public double number() throws IOException {
		return number;
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
	public double increaseRate() throws IOException {
		return increaseRate;
	}

	@Override
	public int increaseInPercent() throws IOException {
		return increaseInPercent;
	}
	
	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		
		Periodicity periodicity = periodicity(activity);
		
		// calculer le nombre
		final BiPeriod period = new BiPeriodOfPeriodicity(date, periodicity);
		
		number = number(period);
		
		// calculer le taux d'évolution
		increaseRate = -777;
		increaseInPercent = -77700;
		if(manageEvolutionPercent()) {
			final double previousNumber;
			if(periodicity.unit() == PeriodicityUnit.FOREVER) {
				previousNumber = 0;
			}			
			else {
				final BiPeriod previousPeriod = new PreviousBiPeriod(period, periodicity);
				previousNumber = number(previousPeriod);
			}	
			
			if(previousNumber == 0)
				increaseRate = -777;
			else
				increaseRate = Indicator.roundNumber((number - previousNumber) / previousNumber, 2);
			
			
			// calculer le taux d'évolution en pourcentage
			if(manageEvolutionPercent())
				increaseInPercent = (int)(increaseRate * 100);
			else
				increaseInPercent = 0;
		}		
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		
		Indicators indicators = activity.indicators();
		NumberOriented newIndicator = (NumberOriented)indicators.add(code(), IndicatorType.NUMBER_ORIENTED);
		newIndicator.update(
				newIndicator.code(), 
				singleLabel(), 
				pluralLabel(), 
				manageEvolutionPercent(), 
				unitySymbol(), 
				symbolPosition(), 
				description()
		);
		return newIndicator;
	}

	@Override
	public void copy(Indicator source) throws IOException {
		NumberOriented copy = (NumberOriented)source;
		update(
			code(), 
			copy.singleLabel(), 
			copy.pluralLabel(), 
			copy.manageEvolutionPercent(), 
			copy.unitySymbol(), 
			copy.symbolPosition(), 
			copy.description()
		);
	}
}
