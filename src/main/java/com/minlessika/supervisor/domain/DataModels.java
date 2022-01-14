package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.utils.CodeContainer;

public interface DataModels extends DomainSet<DataModel, DataModels>, CodeContainer {
	DataModel add(String code, String name, DataModelType type, String description) throws IOException;
}
