package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	comodel=DataFieldOfSheet.class
)
public interface ListDataFieldOfSheet extends DataFieldOfSheet {
	
	@Field(
		label="Sheet to refer",
		rel=Relation.MANY2ONE
	)
	DataSheet sheetToRefer() throws IOException;

	void update(String reference, String value) throws IOException;
	void update(DataSheet sheetToRefer, String value) throws IOException;
	
	void update() throws IOException;
}
