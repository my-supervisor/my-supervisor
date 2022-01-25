package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;

import com.supervisor.sdk.datasource.DomainSet;

public interface DataSheetOfModels extends DomainSet<DataSheet, DataSheetOfModels> {
	DataSheet add(String reference, LocalDate date) throws IOException;
	DataSheet copy(DataSheet source) throws IOException;
}
