package com.supervisor.domain;

import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
	comodel=DataFieldOfSheet.class
)
public interface TableDataFieldOfSheet extends DataFieldOfSheet, TableDataField {	
	TableDataFieldOfSheetRows rows() throws IOException;
}
