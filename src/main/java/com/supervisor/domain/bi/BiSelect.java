package com.supervisor.domain.bi;

import java.util.List;
import java.util.Map;

import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Params;

public interface BiSelect {	
	BiRequest request();
	DataModel view();
	Map<DataField, DataLinkOperator> columns();
	List<DataField> searchableColumns();
	Params params();
	DataDomainDefinition dataDomainDefinition();
	Ordering ordering();
	long start();
	int limit();
}
