package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ParamDataFields extends DomainSet<ParamDataField, ParamDataFields> {
	ParamDataField add(String code, String name, String value, DataFieldType type) throws IOException;
	ParamDataField get(String code) throws IOException;
}
