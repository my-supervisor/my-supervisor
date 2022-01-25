package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface EditableDataFields extends DomainSet<EditableDataField, EditableDataFields> {
	EditableDataField add(String code, String name, DataFieldType type, DataFieldStyle style, UserScope userScope, boolean isMandatory, String description) throws IOException;
	EditableDataField get(String code) throws IOException;
}
