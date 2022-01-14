package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.colors.Color;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.indicator.ChartCamembert;
import com.minlessika.supervisor.indicator.ChartCamembertType;
import com.minlessika.supervisor.indicator.DynamicTable2Col;
import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.GaugeType;
import com.minlessika.supervisor.indicator.GoalNumber;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.indicator.NumberOriented;
import com.minlessika.supervisor.indicator.OrderingMode;
import com.minlessika.supervisor.indicator.SymbolPosition;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;

public final class PxIndicators extends DomainRecordables<Indicator, Indicators> implements Indicators {
	
	private final Activity activity;
	
	public PxIndicators(final RecordSet<Indicator> source) throws IOException {
		this(source, Activity.EMPTY);
	}
	
	public PxIndicators(final Activity activity) throws IOException {
		this(activity.listOf(Indicator.class), activity);
	}
	
	private PxIndicators(final RecordSet<Indicator> source, final Activity activity) throws IOException {
		super(
				IndicatorBase.class, 
				source
		);
		
		this.activity = activity;
		if(activity != Activity.EMPTY) {
			this.source = source.where(Indicator::activity, activity.id());
		}		
	}

	@Override
	protected Indicators domainSetOf(final RecordSet<Indicator> source) throws IOException {
		return new PxIndicators(source, activity);
	}
	
	@Override
	protected Indicator domainOf(final Record<Indicator> record) throws IOException {
		return TypedIndicator.convert(record);			
	}
	
	@Override
	public Indicator add(
			final String code, 
			final String shortName
	) throws IOException {
			
		if(activity == Activity.EMPTY)
			throw new IllegalArgumentException("Création d'un indicateur : vous devez sélectionner une activité !");
		
		// vérifier que l'utilisateur a le droit de créer un nouvel indicateur
		User user = new UserOf(this);
		user.profileOf(Supervisor.NAME)
		    .validateAccessibility("NEW_INDICATOR", String.format("%s", count() + 1));
				
		source.isRequired(Indicator::code, code);
		
		source.mustBeUnique(
				Indicator::code, code, 
				Indicator::activity, activity.id()
		);
		
		IndicatorType type = new PxIndicatorTypes(source.of(IndicatorType.class)).indicatorType(shortName);
		
		int row = 0;
		int col = 0;
		
		if(!isEmpty()) {
			Indicator last = last();
			row = last.row();
			col = last.col() + last.sizeX();
			if(col + type.defaultSizeX() >= 8) {
				row++;
				col=0;
			}
		}		
		
		Record<Indicator> record = source.entryOf(Indicator::code, code)
										 .entryOf(Indicator::type, type.id())
										 .entryOf(Indicator::singleLabel, "Single name")
										 .entryOf(Indicator::pluralLabel, "Plural name")
										 .entryOf(Indicator::description, null)										 
										 .entryOf(Indicator::isTemplate, false)
										 .entryOf(Indicator::activity, activity.id())
									     .entryOf(Indicator::sizeX, type.defaultSizeX())
									     .entryOf(Indicator::sizeY, type.defaultSizeY())
									     .entryOf(Indicator::row, row)
									     .entryOf(Indicator::col, col)
										 .add();

		Indicator item = new IndicatorBase(record);
		
		for (IndicatorTypeDynamicParam param : type.dynamicParams().items()) {
			if(param.code().equals("ENTITY")) {
				item.dynamicParams().put(param, AggregateFunc.NONE);
			}else {
				item.dynamicParams().put(param, AggregateFunc.SUM);
			}			
		}
		
		// création par défaut de l'indicateur spécialisé
		switch (shortName) {
			case IndicatorType.CHART_CAMEMBERT:
				source.of(ChartCamembert.class)
                      .entryOf(ChartCamembert::id, item.id())													 
					  .entryOf(ChartCamembert::camembertType, ChartCamembertType.PIE)
					  .entryOf(ChartCamembert::subLabel, "Sub label")
					  .add();
				break;
			case IndicatorType.DYNAMIC_TABLE_2_COL:
				source.of(DynamicTable2Col.class)
                      .entryOf(DynamicTable2Col::id, item.id())													 
                      .entryOf(DynamicTable2Col::manageEvolutionPercent, false)
					  .entryOf(DynamicTable2Col::unitySymbol, StringUtils.EMPTY)
					  .entryOf(DynamicTable2Col::symbolPosition, SymbolPosition.NONE)
					  .entryOf(DynamicTable2Col::orderingMode, OrderingMode.NATUREL)
					  .entryOf(DynamicTable2Col::nbMaxOfElementsToShow, 5)
					  .add();
				break;
			case IndicatorType.GAUGE:
				source.of(Gauge.class)
                      .entryOf(Gauge::id, item.id())													 
                      .entryOf(Gauge::unitySymbol, StringUtils.EMPTY)
					  .entryOf(Gauge::symbolPosition, SymbolPosition.RIGHT)
					  .entryOf(Gauge::gaugeType, GaugeType.RADIAL)
					  .entryOf(Gauge::minorTicks, 2)
					  .entryOf(Gauge::majorTicks, 10)
					  .entryOf(Gauge::min, 0)
					  .entryOf(Gauge::max, 100)
					  .add();
				break;
			case IndicatorType.GOAL_NUMBER:
				source.of(GoalNumber.class)
                      .entryOf(GoalNumber::id, item.id())													 
                      .entryOf(GoalNumber::unitySymbol, StringUtils.EMPTY)
					  .entryOf(GoalNumber::symbolPosition, SymbolPosition.RIGHT)
					  .entryOf(GoalNumber::color, Color.GREEN)
					  .add();
				break;
			case IndicatorType.NUMBER_ORIENTED:
				source.of(NumberOriented.class)
					  .entryOf(NumberOriented::id, item.id())
					  .entryOf(NumberOriented::manageEvolutionPercent, false)
				      .entryOf(NumberOriented::unitySymbol, "")
				      .entryOf(NumberOriented::symbolPosition, SymbolPosition.RIGHT)
					  .add();
				break;
		default:
			throw new IllegalArgumentException(String.format("L'indicateur de type %s n'est pas pris en charge !", type.toString()));
		}
		
		return domainOf(record);
	}
	
	@Override
	public void remove(Indicator item) throws IOException{
		// remove indicator periodicity
		item.removePeriodicity();
		super.remove(item);
	}

	@Override
	public boolean contains(String code) throws IOException {
		return where(Indicator::code, code).any();
	}
}
