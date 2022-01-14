package com.minlessika.supervisor.domain.bi.impl;

import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Ordering;
import com.minlessika.sdk.pgsql.PgSimpleOrdering;
import com.minlessika.sdk.time.Periodicity;
import com.minlessika.supervisor.domain.*;
import com.minlessika.supervisor.domain.bi.*;
import com.minlessika.supervisor.domain.impl.DefaultDataFieldValue;
import com.minlessika.supervisor.domain.impl.FieldDate;
import com.minlessika.supervisor.domain.impl.FieldID;
import com.minlessika.supervisor.domain.impl.ParamsImpl;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class QueryScalarIndicator implements QueryScalar {

	private final DataWarehouse dWh;
	private final Indicator indicator;

	/**
	 * Period.
	 */
	private final BiPeriod period;

	private final IndicatorDynamicParam select;
	
	public QueryScalarIndicator(
		final Indicator indicator,
		final IndicatorDynamicParam select,
		final BiPeriod period
	) {
		this.dWh = new PgDataWarehouse(indicator.base());
		this.indicator = indicator;
		this.select = select;
		this.period = period;
	}
	
	@Override
	public Object result() throws IOException {
		
		final AggregateFunc aggregate = select.aggregateFunc();
		
		if(aggregate == AggregateFunc.NONE)
			throw new IllegalArgumentException("BI : vous devez spécifier une fonction d'aggrégation !");
		
		Ordering ordering = Ordering.EMPTY;
		if(aggregate == AggregateFunc.FIRST_VALUE) {
			ordering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.ASC, Arrays.asList("date", "id"));
		} else if(aggregate == AggregateFunc.LAST_VALUE) {
			ordering = new PgSimpleOrdering(StringUtils.EMPTY, OrderDirection.DESC, Arrays.asList("date", "id"));
		}
		
		final Object none = new DefaultDataFieldValue(select.type()).objectValue();		
		if(indicator.links().actives().isEmpty())
			return none;
		
		final BiRequest request = new BiRequestImpl(StringUtils.EMPTY, period, 0L, 0, ordering);
		for (DataLink link : indicator.links().actives().items()) {
			final DataModel model = link.model();
			
			final Map<DataField, DataLinkOperator> columns = new LinkedHashMap<>();
			for (MappedDataField mapping : link.mappings().items()) {
				columns.put(mapping.fieldToUse(), mapping.operator());
			}
			
			if(aggregate == AggregateFunc.FIRST_VALUE || aggregate == AggregateFunc.LAST_VALUE) {
				columns.put(new FieldID(), DataLinkOperator.NONE);
				columns.put(new FieldDate(), DataLinkOperator.NONE);
			}
			
			request.selects()
			       .add(
		    		   model, 
		    		   columns, 
		    		   Arrays.asList(), 
		    		   new ParamsImpl(indicator.activity().params().items(), link.params().items(), model.fields().params().items()),
		    		   link.dataDomainDefinition(),
		    		   Ordering.EMPTY,
		    		   0L,
		    		   0
			    	);			
		}
				
		DataField field = DataField.EMPTY;
		DataLinkOperator operator = DataLinkOperator.NONE;
		
		for (MappedDataField mapping : indicator.links().actives().first().mappings().items()) {
			if(mapping.param().equals(select)) {
				operator = mapping.operator();
				field = mapping.fieldToUse();
				break;
			}
		}
		
		if(field == DataField.EMPTY)
			throw new IllegalArgumentException("Param to aggregate must be one of indicator parameters !"); 
		
		request.columns()
			   .add(
				    new BiSimpleColumn(
				    	"value", 
				    	new MarkupIndicatorParamBodyPrinter(select, operator, field).toText(), 
				    	aggregate
				    )
			   );	
		if(dWh.query(request).rows().isEmpty())
			return none;
		else {
			final Object result = dWh.query(request).rows().get(0).get("value").value();
			return result == null ? none : result;
		}		
	}

	@Override
	public QueryScalar with(
		final LocalDate today,
		final Periodicity periodicity
	) throws IOException {
		return new QueryScalarIndicator(indicator, select, period);
	}

}
