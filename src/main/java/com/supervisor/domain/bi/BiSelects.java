package com.supervisor.domain.bi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Params;

public interface BiSelects {
	List<BiSelect> items();
	BiSelect add(DataModel view, Map<DataField, DataLinkOperator> columns, List<DataField> searchableColumns, Params params, DataDomainDefinition dataDomainDefinition, final Ordering ordering, final long start, final int limit) throws IOException;
}
