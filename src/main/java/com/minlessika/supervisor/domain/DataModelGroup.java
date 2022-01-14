package com.minlessika.supervisor.domain;

import java.util.List;

public interface DataModelGroup {
	int level();
	List<DataModel> items();
}
