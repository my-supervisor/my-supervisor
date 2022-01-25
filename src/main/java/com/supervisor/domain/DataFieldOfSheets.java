package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface DataFieldOfSheets extends DomainSet<DataFieldOfSheet, DataFieldOfSheets> {
	SimpleDataFieldOfSheet add(SimpleDataField field, String value) throws IOException;
	ListDataFieldOfSheet add(ListDataField field, String reference, String value) throws IOException;
	ListDataFieldOfSheet add(ListDataField field, DataSheet sheetToRefer, String value) throws IOException;
	TableDataFieldOfSheet add(TableDataField field) throws IOException;
	DataFieldOfSheet get(String code) throws IOException;
	DataFieldOfSheet put(String code, String reference, String value) throws IOException;
}
