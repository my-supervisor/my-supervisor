package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=DataFieldOfSheet.class
)
public interface ListDataFieldOfSheet extends DataFieldOfSheet {
	
	@Field(
		label="Sheet to refer",
		rel= Relation.MANY2ONE
	)
	DataSheet sheetToRefer() throws IOException;

	void update(String reference, String value) throws IOException;
	void update(DataSheet sheetToRefer, String value) throws IOException;
	
	void update() throws IOException;
}
