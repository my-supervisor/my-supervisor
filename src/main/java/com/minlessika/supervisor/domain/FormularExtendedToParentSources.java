package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface FormularExtendedToParentSources extends DomainSet<FormularExtendedToParentSource, FormularExtendedToParentSources> {
	FormularExtendedToParentSources actives() throws IOException;
	FormularExtendedToParentSource add(ListDataFieldSource listSource, EditableDataField field) throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
	FormularExtendedToParentSource get(ListDataFieldSource source) throws IOException;
}
