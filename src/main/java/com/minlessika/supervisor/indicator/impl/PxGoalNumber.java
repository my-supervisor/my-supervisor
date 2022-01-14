package com.minlessika.supervisor.indicator.impl;

import com.minlessika.sdk.colors.Color;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.impl.BiPeriodOfPeriodicity;
import com.minlessika.supervisor.domain.bi.impl.QueryScalarIndicator;
import com.minlessika.supervisor.indicator.*;

import java.io.IOException;
import java.time.LocalDate;

public final class PxGoalNumber extends IndicatorWrap implements GoalNumber {

	private double number = 0;
	private double goal = 0;
	private int numberInPercent = 0;
	private final Record<GoalNumber> record;
	
	public PxGoalNumber(final Indicator origin) throws IOException {
		super(origin);
		
		this.record = origin.listOf(GoalNumber.class)
                			.get(origin.id());
	}
	
	@Override
	public void update(
			String code, 
			Color color,
			String unitySymbol, 
			SymbolPosition symbolPosition, 
			String label, 
			String description
	) throws IOException {
		
		record.mustCheckThisCondition(
				color != Color.NONE, 
				"Vous devez spécifier une couleur !"
		);
		
		origin.update(code, label, label, description);
		
		record.entryOf(GoalNumber::unitySymbol, unitySymbol)
			  .entryOf(GoalNumber::symbolPosition, symbolPosition)
			  .entryOf(GoalNumber::color, color)
			  .update();
	}

	@Override
	public String unitySymbol() throws IOException {
		return record.valueOf(GoalNumber::unitySymbol);
	}

	@Override
	public SymbolPosition symbolPosition() throws IOException {
		return record.valueOf(GoalNumber::symbolPosition);
	}

	@Override
	public String label() throws IOException {
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
				dynamicParams().get("QTE"),
				period
			).result();
	}
	
	private double goal(final BiPeriod period) throws IOException {
		return (Double)
			new QueryScalarIndicator(
		this,
				dynamicParams().get("GOAL"),
				period
			).result();
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		
		BasePeriodicity periodicity = periodicity(activity);
		final BiPeriod period = new BiPeriodOfPeriodicity(date, periodicity);
		number = number(period);
		goal = goal(period);
		
		if(goal == 0)
			numberInPercent = -77700;
		else
			numberInPercent = (int) (number / goal * 100);
	}

	@Override
	public Color color() throws IOException {
		return record.valueOf(GoalNumber::color);
	}

	@Override
	public int numberInPercent() throws IOException {
		return numberInPercent;
	}

	@Override
	public void update(String code, String singleLabel, String pluralLabel, String description) throws IOException {
		throw new UnsupportedOperationException("GoalNumber:update#Indicator");
	}

	@Override
	public double goal() throws IOException {
		return goal;
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		
		Indicators indicators = activity.indicators();
		GoalNumber newIndicator = (GoalNumber)indicators.add(code(), IndicatorType.GOAL_NUMBER);
		newIndicator.update(
				newIndicator.code(), 
				color(), 
				unitySymbol(), 
				symbolPosition(), 
				label(), 
				description()
		);
		
		return newIndicator;
	}

	@Override
	public void copy(Indicator source) throws IOException {
		GoalNumber copy = (GoalNumber)source;
		update(
			code(), 
			copy.color(), 
			copy.unitySymbol(), 
			copy.symbolPosition(), 
			copy.label(), 
			copy.description()
		);
	}
}
