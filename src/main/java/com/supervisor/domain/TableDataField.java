package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=EditableDataField.class
)
public interface TableDataField extends EditableDataField {
	
	@Field(
		label="Structure", 
		rel= Relation.MANY2ONE
	)
	DataSheetModel structure() throws IOException;
	
	List<EditableDataField> columns() throws IOException;
	
	void update(String code, String name, String description) throws IOException;
}
