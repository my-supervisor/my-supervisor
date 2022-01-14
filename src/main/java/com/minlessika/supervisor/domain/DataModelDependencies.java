package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.util.List;

public interface DataModelDependencies {
	List<DataModel> items() throws IOException;
	boolean contains(DataModel model) throws IOException;
}
