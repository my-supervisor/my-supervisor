package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.datasource.comparators.Comparator;

public interface ModelFilterConditions extends DomainSet<ModelFilterCondition, ModelFilterConditions> {
	ModelFilterCondition add(DataField field, Comparator comparator, String value) throws IOException;
}
