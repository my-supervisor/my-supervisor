package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface DataLinks extends DomainSet<DataLink, DataLinks> {
	DataLink add(String name, DataModel model) throws IOException;
	DataLinks actives() throws IOException;
}
