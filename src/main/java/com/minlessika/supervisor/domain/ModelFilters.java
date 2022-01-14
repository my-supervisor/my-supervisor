package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ModelFilters extends DomainSet<ModelFilter, ModelFilters> {
	ModelFilter add() throws IOException;
}
