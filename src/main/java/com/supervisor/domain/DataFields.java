package com.supervisor.domain;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface DataFields extends DomainSet<DataField, DataFields>, CodeContainer {
	
	UUID add(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException;
	DataField get(String code) throws IOException;
	
	EditableDataFields editables() throws IOException;
	EditableDataFields scalarEditableFields() throws IOException;
	SimpleDataFields simples() throws IOException;
	ListDataFields lists() throws IOException;
	TableDataFields tables() throws IOException;
	FormularDataFields formulars() throws IOException;
	ParamDataFields params() throws IOException;
}
