package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;

import com.supervisor.sdk.datasource.DomainSet;

public interface TableDataFieldOfSheetRows extends DomainSet<TableDataFieldOfSheetRow, TableDataFieldOfSheetRows> {
	TableDataFieldOfSheetRow add() throws IOException;
	TableDataFieldOfSheetRow add(String reference, LocalDate date) throws IOException;
	TableDataFieldOfSheetRow copy(TableDataFieldOfSheetRow source) throws IOException;
}
