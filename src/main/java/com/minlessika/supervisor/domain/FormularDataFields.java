package com.minlessika.supervisor.domain;

import java.io.IOException;
import com.minlessika.sdk.datasource.DomainSet;

public interface FormularDataFields extends DomainSet<FormularDataField, FormularDataFields> {
	FormularDataField add(String name, String code, DataFieldType type) throws IOException;
	FormularDataField get(String code) throws IOException;
}