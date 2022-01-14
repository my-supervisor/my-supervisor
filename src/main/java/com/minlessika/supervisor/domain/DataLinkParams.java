package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface DataLinkParams extends DomainSet<DataLinkParam, DataLinkParams> {
	DataLinkParam put(ParamDataField param, String value) throws IOException;
}
