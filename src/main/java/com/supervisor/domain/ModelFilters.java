package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface ModelFilters extends DomainSet<ModelFilter, ModelFilters> {
	ModelFilter add() throws IOException;
}
