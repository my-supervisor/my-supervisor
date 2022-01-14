package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;
import com.minlessika.sdk.datasource.comparators.Comparator;

public interface FormularExtendedToModelSources extends DomainSet<FormularExtendedToModelSource, FormularExtendedToModelSources> {
	FormularExtendedToModelSources actives() throws IOException;
	FormularExtendedToModelSource add(DataSheetModel model, EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend) throws IOException;
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	FormularExtendedToModelSource get(DataSheetModel model) throws IOException;
}
