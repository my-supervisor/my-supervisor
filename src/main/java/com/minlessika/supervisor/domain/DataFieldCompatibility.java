package com.minlessika.supervisor.domain;

import java.util.List;

public interface DataFieldCompatibility {
	DataField source();
	List<DataField> targets();
}
