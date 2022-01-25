package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface DataModels extends DomainSet<DataModel, DataModels>, CodeContainer {
	DataModel add(String code, String name, DataModelType type, String description) throws IOException;
}
