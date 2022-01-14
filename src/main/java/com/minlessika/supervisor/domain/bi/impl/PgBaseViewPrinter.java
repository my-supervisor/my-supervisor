package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;
import com.minlessika.sdk.pgsql.PgBaseScheme;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.bi.Printer;

public final class PgBaseViewPrinter implements Printer {

	private final DataModel model;
	private final Printer select;	
	
	public PgBaseViewPrinter(final DataModel model, final Printer select) {
		this.model = model;
		this.select = select;
	}
	
	@Override
	public String toText() throws IOException {

		String dataSheet = PgBaseScheme.nameOfClazz(DataSheet.class);
		String dataFieldOfSheet = PgBaseScheme.nameOfClazz(DataFieldOfSheet.class);
		String dataField = PgBaseScheme.nameOfClazz(DataField.class);
		String dataSheetModel = PgBaseScheme.nameOfClazz(DataSheetModel.class);		
		
		final String modelClause;
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			modelClause = String.format("sheet.model_id = %s", model.id());
		} else {
			String aggregatedModel = PgBaseScheme.nameOfClazz(AggregatedModel.class);
			modelClause = String.format(
								"sheet.model_id in (\r\n" + 
								"	select core_model_id from %s where id = %s \r\n" + 
								")", 
								aggregatedModel,
								model.id()
						  );
		}
		
		return String.format(
				"select sheet.id, sheet.date, df.code, mdfs.value, %s \r\n"
			  + "from %s as mdfs \r\n"
			  + "left join %s as df on df.id = mdfs.origin_field_id \r\n"
			  + "left join %s as sheet on sheet.id = mdfs.sheet_id \r\n"
			  + "left join %s as model on model.id = sheet.model_id \r\n"
			  + "where %s \r\n"
			  + "and df.code in ('REF') \r\n",
			  select.toText(),				   				    
			  dataFieldOfSheet,
			  dataField,
			  dataSheet,
			  dataSheetModel,			  
			  modelClause
	    );
	}

}
