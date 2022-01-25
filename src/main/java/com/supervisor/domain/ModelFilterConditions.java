package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.sdk.datasource.comparators.Comparator;

public interface ModelFilterConditions extends DomainSet<ModelFilterCondition, ModelFilterConditions> {
	ModelFilterCondition add(DataField field, Comparator comparator, String value) throws IOException;
}
