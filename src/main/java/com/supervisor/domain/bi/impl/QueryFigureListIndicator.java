package com.supervisor.domain.bi.impl;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.MappedDataField;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.pgsql.PgSimpleOrdering;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.domain.impl.ParamsImpl;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.BiRequest;
import com.supervisor.domain.bi.BiRow;
import com.supervisor.domain.bi.DataWarehouse;
import com.supervisor.domain.bi.QueryFigureList;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class QueryFigureListIndicator implements QueryFigureList {

	private final DataWarehouse dWh;
	private final Indicator indicator;

	/**
	 * Period.
	 */
	private final BiPeriod period;

	private final IndicatorDynamicParam entity;
	private final IndicatorDynamicParam value;
	private final int limit;
	private final Ordering ordering;
	
	public QueryFigureListIndicator(final Indicator indicator, final IndicatorDynamicParam entity, final IndicatorDynamicParam value, final BiPeriod period) {
		this(indicator, entity, value, period, 0, Ordering.EMPTY);
	}
	
	public QueryFigureListIndicator(final Indicator indicator, final IndicatorDynamicParam entity, final IndicatorDynamicParam value, final BiPeriod period, final int limit, final Ordering ordering) {
		this.dWh = new PgDataWarehouse(indicator.base()); 
		this.indicator = indicator;
		this.entity = entity;
		this.value = value;
		this.period = period;
		this.limit = limit;
		this.ordering = ordering;
	}
	
	@Override
	public Map<String, Double> figures() throws IOException {
		
		if(indicator.links().actives().isEmpty())
			return new LinkedHashMap<>();
		
		final BiRequest request = new BiRequestImpl(StringUtils.EMPTY, period, 0L, limit, ordering);
		for (DataLink link : indicator.links().actives().items()) {
			final DataModel model = link.model();
			
			final Map<DataField, DataLinkOperator> columns = new LinkedHashMap<>();
			for (MappedDataField mapping : link.mappings().items()) {
				columns.put(mapping.fieldToUse(), mapping.operator());
			}
			
			Ordering linkOrdering = Ordering.EMPTY;
			long linkStart = 0L;
			int linkLimit = 0;
			final AggregateFunc aggregateFunc = indicator.dynamicParams().get("QTE").aggregateFunc();
			if(aggregateFunc == AggregateFunc.FIRST_VALUE) {
				linkOrdering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.ASC, Arrays.asList("date", "id"));
				linkStart = 1;
				linkLimit = 1;
			} else if(aggregateFunc == AggregateFunc.LAST_VALUE) {
				linkOrdering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.DESC, Arrays.asList("date", "id"));
				linkStart = 1;
				linkLimit = 1;
			}
			
			request.selects()
			       .add(
		    		   model, 
		    		   columns, 
		    		   Arrays.asList(), 
		    		   new ParamsImpl(indicator.activity().params().items(), link.params().items(), model.fields().params().items()),
		    		   link.dataDomainDefinition(),
		    		   linkOrdering,
		    		   linkStart,
		    		   linkLimit
			    	);			
		}
				
		request.columns()
		   .add(
			    new BiSimpleColumn(
		    		"entity", 
		    		new MarkupIndicatorParamBodyPrinter(entity, operatorOf(entity), fieldOf(entity)).toText(), 
		    		entity.aggregateFunc()
			    )
		   );		
		
		if(value.aggregateFunc() == AggregateFunc.FIRST_VALUE || value.aggregateFunc() == AggregateFunc.LAST_VALUE) {
			request.columns()
			   .add(
				   new BiSimpleColumn(
			    		"value", 
			    		new MarkupIndicatorParamBodyPrinter(value, operatorOf(value), fieldOf(value)).toText(), 
			    		AggregateFunc.SUM
				    )
			   );
		} else {
			request.columns()
			   .add(
				    new BiSimpleColumn(
			    		"value", 
			    		new MarkupIndicatorParamBodyPrinter(value, operatorOf(value), fieldOf(value)).toText(), 
			    		value.aggregateFunc()
				    )
			   );
		}
		
		final Map<String, Double> results = new LinkedHashMap<>();
		for (BiRow row : dWh.query(request).rows()) {
			results.put(row.get("entity").value(), row.get("value").value());
		}
		
		return results;
	}

	@Override
	public QueryFigureList with(LocalDate today, Periodicity periodicity) throws IOException {
		return new QueryFigureListIndicator(
			indicator,
			entity,
			value,
			new BiPeriodOfPeriodicity(today, periodicity),
			limit,
			ordering
		);
	}
	
	private DataField fieldOf(IndicatorDynamicParam param) throws IOException {
		
		DataField field = DataField.EMPTY;
		
		for (MappedDataField mapping : indicator.links().actives().first().mappings().items()) {
			if(mapping.param().equals(param)) {
				field = mapping.fieldToUse();
				break;
			}
		}
		
		if(field == DataField.EMPTY)
			throw new IllegalArgumentException("Param to aggregate must be one of indicator parameters !");
		
		return field;
	}

	private DataLinkOperator operatorOf(IndicatorDynamicParam param) throws IOException {
		
		DataLinkOperator operator = DataLinkOperator.NONE;
		
		for (MappedDataField mapping : indicator.links().actives().first().mappings().items()) {
			if(mapping.param().equals(param)) {
				operator = mapping.operator();
				break;
			}
		}
		
		return operator;
	}

}
