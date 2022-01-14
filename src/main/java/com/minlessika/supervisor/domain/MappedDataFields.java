package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;

public interface MappedDataFields extends DomainSet<MappedDataField, MappedDataFields> {
	MappedDataField put(IndicatorDynamicParam param, DataLinkOperator operator, DataField field) throws IOException;
}