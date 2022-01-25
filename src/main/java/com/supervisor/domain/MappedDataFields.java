package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;
import com.supervisor.indicator.IndicatorDynamicParam;

public interface MappedDataFields extends DomainSet<MappedDataField, MappedDataFields> {
	MappedDataField put(IndicatorDynamicParam param, DataLinkOperator operator, DataField field) throws IOException;
}