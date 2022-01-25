package com.supervisor.domain.bi.impl;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Params;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.BiSelect;
import com.supervisor.domain.bi.Printer;
import com.supervisor.domain.bi.PrinterRow;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

public final class PgModelViewPrinter implements Printer {

	private final BiSelect select;
	
	public PgModelViewPrinter(final BiSelect select) {
		this.select = select;
	}
	
	@Override
	public String toText() throws IOException {
		
		final DataModel model = select.view();
		final BiPeriod period = select.request().period();
		final Params params = select.params();
		
		final List<PrinterRow> rows = new PrinterSorterImpl(model, params, period).items();
		Printer view = new PgBaseViewPrinter(
							model, 
							new PgSelectPrinter(rows.get(0).items())
					   );
		
		for (int i = 1; i < rows.size(); i++) {
			view = new PgViewPrinter(
						view, 
						new PgSelectPrinter(rows.get(i).items())
				   );
		}
			
		String searching = StringUtils.EMPTY;
		final String filter = select.request().filter();
		final List<DataField> searchableColumns = select.searchableColumns();
		if(StringUtils.isNotBlank(filter) && !searchableColumns.isEmpty()) {
			
			String condition = StringUtils.EMPTY;
			for (DataField field : searchableColumns) {
				
				final String subCondition = String.format("\"%s\"", field.code()) + " ilike '%" + filter + "%'";
				if(StringUtils.isBlank(condition))
					condition = subCondition;
				else
					condition = String.format("%s or %s", condition, subCondition);
			}
			
			searching = String.format(
								"%s \r\n"
							  + "where %s", 
							  searching,
							  condition
						);
		}
		
		return String.format(
			    "%s \r\n"
			  + "%s \r\n",
			    view.toText(),
			  	searching
	    );
	}

}
