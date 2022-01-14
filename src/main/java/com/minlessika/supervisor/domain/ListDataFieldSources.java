package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ListDataFieldSources extends DomainSet<ListDataFieldSource, ListDataFieldSources> {
	ListDataFieldSources actives() throws IOException;
	ListDataFieldSource whichBasedOn(DataModel model) throws IOException;
	ListDataFieldSource add(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
}
