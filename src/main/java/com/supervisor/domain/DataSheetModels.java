package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface DataSheetModels extends DomainSet<DataSheetModel, DataSheetModels>, CodeContainer {
	DataSheetModel add(String code, String name, String description) throws IOException;
}
