package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.utils.CodeContainer;

public interface DataSheetModels extends DomainSet<DataSheetModel, DataSheetModels>, CodeContainer {
	DataSheetModel add(String code, String name, String description) throws IOException;
}
