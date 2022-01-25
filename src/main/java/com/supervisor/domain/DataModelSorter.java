package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

public interface DataModelSorter {
	List<DataModel> items() throws IOException;
	List<DataModelGroup> groups() throws IOException;
}
