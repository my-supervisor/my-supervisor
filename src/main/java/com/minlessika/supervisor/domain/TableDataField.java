package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	comodel=EditableDataField.class
)
public interface TableDataField extends EditableDataField {
	
	@Field(
		label="Structure", 
		rel=Relation.MANY2ONE
	)
	DataSheetModel structure() throws IOException;
	
	List<EditableDataField> columns() throws IOException;
	
	void update(String code, String name, String description) throws IOException;
}
