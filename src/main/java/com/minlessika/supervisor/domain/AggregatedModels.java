package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.utils.CodeContainer;

public interface AggregatedModels extends DomainSet<AggregatedModel, AggregatedModels>, CodeContainer {
	AggregatedModel add(String code, DataModel model) throws IOException;
}
