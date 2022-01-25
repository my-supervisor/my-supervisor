package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

public interface DataFieldSorter {
	List<DataField> items() throws IOException;
	List<DataFieldGroup> groups() throws IOException;
}
