package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.minlessika.sdk.datasource.comparators.BasicMatcher;
import com.minlessika.sdk.datasource.conditions.Filter;
import com.minlessika.sdk.datasource.conditions.FilterWrap;
import com.minlessika.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.minlessika.sdk.datasource.conditions.pgsql.PgFilter;
import com.minlessika.sdk.datasource.conditions.pgsql.PgSimpleCondition;
import com.minlessika.sdk.metadata.BaseType;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DateMacroArg;
import com.minlessika.supervisor.domain.Params;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ModelFilterCondition;
import com.minlessika.supervisor.domain.ModelFilters;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.bi.BiPeriod;

public final class PgFilterOfModel extends FilterWrap<DataFieldOfSheet> {

	public PgFilterOfModel(final ModelFilters filters, final Params params, final BiPeriod period) throws IOException {
		super(convert(filters, params, period));
	}
	
	private static Filter<DataFieldOfSheet> convert(final ModelFilters filters, final Params params, final BiPeriod period) throws IOException {
		
		Filter<DataFieldOfSheet> filter = new PgFilter<>(DataFieldOfSheet.class); 
		
		for (ModelFilter ruleFilter : filters.items()) {
			Filter<DataFieldOfSheet> orFilter = new PgFilter<>(DataFieldOfSheet.class, ConditionOperator.OR);
			List<ParamDataField> ruleParams = ruleFilter.model().params().items();
			
			for (ModelFilterCondition cond : ruleFilter.conditions().items()) {

				final DataField field = cond.field();
				BaseType fType = new PgTypeOfDataFieldType(field.type());
				orFilter.add(
	                		  new PgSimpleCondition(
            				    String.format("sheet.\"%s\"", field.code()),
                				DataFieldOfSheet.class,
                				DataFieldOfSheet::value, 							        		
                				new BasicMatcher(
									cond.comparator(),
									adaptValue(cond.value(), ruleParams, params, period)
								),
                				fType.name()
		                	  )
		                  );				
			}
			
			filter.append(orFilter);
		}
		
		return filter; 
	}
	
	private static String adaptValue(String value, List<ParamDataField> ruleParams, Params params, final BiPeriod period) throws IOException {
		
		String newValue = value;
		
		FilterParamArg pArg = new FilterParamArg(newValue, ruleParams);
		if(pArg.isValid()) {
			ParamDataField param = pArg.param();
			newValue = String.format("%s", params.get(param.id()).value());
		}else {
			// Ca peut être une date macro
			DateMacroArg dMArg = new DateMacroArgImpl(newValue, period);
			if(dMArg.isValid()) {
				newValue = dMArg.toDate().toString();
			}
		}
			
		return newValue;
	}
}
