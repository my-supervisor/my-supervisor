package com.minlessika.supervisor.domain.bi.impl;

import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.minlessika.sdk.datasource.conditions.pgsql.PgFilter;
import com.minlessika.supervisor.domain.*;
import com.minlessika.supervisor.domain.bi.BiPeriod;
import com.minlessika.supervisor.domain.bi.Printer;
import com.minlessika.supervisor.domain.bi.PrinterRow;
import com.minlessika.supervisor.domain.bi.PrinterSorter;
import com.minlessika.supervisor.domain.impl.DataFieldSortingOnDependency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PrinterSorterImpl implements PrinterSorter {

	private final DataModel model;
	private final BiPeriod period;
	private final Params params;
	
	public PrinterSorterImpl(final DataModel model, final Params params, final BiPeriod period) {
		this.model = model;
		this.period = period;
		this.params = params;
	}
	
	@Override
	public List<PrinterRow> items() throws IOException {
		
		final List<PrinterRow> rows = new ArrayList<>();
		
		final List<DataField> dataFields = model.fields()
											    .where(
												   new PgFilter<>(DataField.class, ConditionOperator.OR)
													   .add(DataField::style, Matchers.equalsTo(DataFieldStyle.LIST))
													   .add(DataField::style, Matchers.equalsTo(DataFieldStyle.SIMPLE))
													   .add(DataField::style, Matchers.equalsTo(DataFieldStyle.PARAMETER))
													   .add(DataField::style, Matchers.equalsTo(DataFieldStyle.FORMULAR))
											     ).items();
		
		final DataFieldSorter sorter = new DataFieldSortingOnDependency(dataFields);
		final List<DataFieldGroup> dataFieldGroups = sorter.groups();
		
		// others levels
		for (int i = 0; i < dataFieldGroups.size(); i++) {
			final List<Printer> printersi = new ArrayList<>();
			final DataFieldGroup groupi = dataFieldGroups.get(i);
			for (DataField dataField : groupi.items()) {
				if(dataField.style() == DataFieldStyle.FORMULAR) {
					printersi.add(new PgFormularDataFieldPrinter((FormularDataField)dataField, params, period));
				} else {
					printersi.add(new PgDataFieldPrinter(dataField, params, period));
				}				
			}
			
			final PrinterRow rowi = new PrinterRowImpl(i, printersi);
			rows.add(rowi);
		}
		
		return rows;
	}

}
