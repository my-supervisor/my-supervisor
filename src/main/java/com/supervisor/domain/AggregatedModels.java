package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.utils.CodeContainer;

public interface AggregatedModels extends DomainSet<AggregatedModel, AggregatedModels>, CodeContainer {
	AggregatedModel add(String code, DataModel model) throws IOException;
}
