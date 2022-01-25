package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface TableDataFields extends DomainSet<TableDataField, TableDataFields> {
	TableDataField add(String code, String name, boolean isMandatory, String description) throws IOException;
	TableDataField get(String code) throws IOException;
}
