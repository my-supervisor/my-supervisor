package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.datasource.DomainSet;

public interface DataFieldDependencies extends DomainSet<DataField, DataFieldDependencies> {
	List<EditableDataField> editables() throws IOException;
	List<FormularDataField> formulars() throws IOException;
	List<ParamDataField> params() throws IOException;
}
