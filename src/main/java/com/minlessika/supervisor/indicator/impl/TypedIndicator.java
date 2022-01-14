package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorType;

public final class TypedIndicator {
	
	private TypedIndicator() {
		
	}
	
	public static Indicator convert(Indicator origin) throws IOException {
		
		Indicator indicator;
		
		IndicatorType type = origin.type();
		
		switch (type.shortName()) {
			case IndicatorType.NUMBER_ORIENTED:
				indicator = new PxNumberOriented(origin);
				break;
			case IndicatorType.CHART_CAMEMBERT:
				indicator = new PxChartCamembert(origin);
				break;
			case IndicatorType.GAUGE:
				indicator = new PxGauge(origin);
				break;
			case IndicatorType.GOAL_NUMBER:
				indicator = new PxGoalNumber(origin);
				break;
			case IndicatorType.DYNAMIC_TABLE_2_COL:
				indicator = new PxDynamicTable2Col(origin);
				break;
			default:
				throw new IllegalArgumentException(String.format("L'indicateur de type %s n'est pas pris en charge !", type.toString()));
		}
		
		return indicator;
	}
	
	public static Indicator convert(final Record<Indicator> source) throws IOException {
		Indicator origin = new IndicatorBase(source);
		return convert(origin);
	}
}
