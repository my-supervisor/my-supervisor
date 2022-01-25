package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface DataFields extends DomainSet<DataField, DataFields>, CodeContainer {
	
	Long add(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException;
	DataField get(String code) throws IOException;
	
	EditableDataFields editables() throws IOException;
	EditableDataFields scalarEditableFields() throws IOException;
	SimpleDataFields simples() throws IOException;
	ListDataFields lists() throws IOException;
	TableDataFields tables() throws IOException;
	FormularDataFields formulars() throws IOException;
	ParamDataFields params() throws IOException;
}
