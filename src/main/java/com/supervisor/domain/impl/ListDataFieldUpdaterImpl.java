package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataFieldOfSheet;
import com.supervisor.domain.ListDataFieldUpdater;

public final class ListDataFieldUpdaterImpl implements ListDataFieldUpdater {

	private final DataSheet sheetUpdated;
	
	public ListDataFieldUpdaterImpl(final DataSheet sheetUpdated) {
		this.sheetUpdated = sheetUpdated;
	}
	
	@Override
	public List<DataSheet> update() throws IOException {
		
		final List<DataSheet> sheetsUpdated = new ListOfUniqueRecord<>();
		
		// find lists that depend on field updated
		final Table table = new TableImpl(DataFieldOfSheet.class);
		final String viewScript = String.format(
										"(\r\n" + 
										"	select entity.*, df.code, df.model_id, edf.no, df.type, df.style, df.name, edf.user_scope \r\n" + 
										"	from %s as entity \r\n" + 
										"	left join %s as df on df.id = entity.origin_field_id \r\n" + 
										"	left join %s as edf on edf.id = df.id \r\n" +
										") as %s \r\n",
										table.name(),
										new TableImpl(DataField.class).name(),
										new TableImpl(EditableDataField.class).name(),
										table.name()
								  );
		
		final RecordSet<ListDataFieldOfSheet> listRecords = sheetUpdated.base()
				                                              	 		.select(ListDataFieldOfSheet.class, viewScript)
			                                              	 			.where(ListDataFieldOfSheet::sheetToRefer, sheetUpdated.id());
		
		for (Record<ListDataFieldOfSheet> record : listRecords.items()) {
			final ListDataFieldOfSheet list = new PxListDataFieldOfSheet(record);
			list.update();	
			sheetsUpdated.add(list.sheet());
		}
		
		return sheetsUpdated;
	}

}
