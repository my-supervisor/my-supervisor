package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.DataSheet;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	name="supervisor_data_sheet_shared", 
	label="Data sheet shared",
	comodel=DataSheet.class
)
public interface DataSheetShared extends DataShared<DataSheet> {
	
	@Field(
			label="Modèle",
			rel= Relation.MANY2ONE
	)
	DataSheet template() throws IOException;
}
