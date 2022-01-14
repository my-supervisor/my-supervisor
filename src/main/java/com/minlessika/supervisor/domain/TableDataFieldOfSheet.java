package com.minlessika.supervisor.domain;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	comodel=DataFieldOfSheet.class
)
public interface TableDataFieldOfSheet extends DataFieldOfSheet, TableDataField {	
	TableDataFieldOfSheetRows rows() throws IOException;
}
