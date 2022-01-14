package com.minlessika.supervisor.domain.bi;

import java.util.List;
import java.util.Map;

import com.minlessika.sdk.datasource.Ordering;
import com.minlessika.supervisor.domain.DataDomainDefinition;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataLinkOperator;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.Params;

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
