package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.DataSheet;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_data_sheet_shared", 
	label="Data sheet shared",
	comodel=DataSheet.class
)
public interface DataSheetShared extends DataShared<DataSheet> {
	
	@Field(
			label="Modèle",
			rel=Relation.MANY2ONE
	)
	DataSheet template() throws IOException;
}
