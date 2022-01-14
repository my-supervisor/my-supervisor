package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	comodel=DataSheet.class
)
public interface TableDataFieldOfSheetRow extends DataSheet {
	
	@Field(
		label="Table", 
		rel=Relation.MANY2ONE
	)
	TableDataFieldOfSheet table() throws IOException;	
}
