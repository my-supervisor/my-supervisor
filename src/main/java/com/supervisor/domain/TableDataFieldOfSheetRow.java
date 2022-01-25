package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=DataSheet.class
)
public interface TableDataFieldOfSheetRow extends DataSheet {
	
	@Field(
		label="Table", 
		rel= Relation.MANY2ONE
	)
	TableDataFieldOfSheet table() throws IOException;	
}
