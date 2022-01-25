package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface SimpleDataFields extends DomainSet<SimpleDataField, SimpleDataFields> {
	SimpleDataField add(String code, String name, DataFieldType type, boolean isMandatory, String defaultValue, String description) throws IOException;
	SimpleDataField get(String code) throws IOException;
}
