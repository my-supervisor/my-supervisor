package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface ListDataFields extends DomainSet<ListDataField, ListDataFields> {
	ListDataField add(String code, String name, DataFieldType type, boolean isMandatory, String description) throws IOException;
	ListDataField get(String code) throws IOException;
}
