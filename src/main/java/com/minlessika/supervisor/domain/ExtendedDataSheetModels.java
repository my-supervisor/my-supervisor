package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.util.List;

public interface ExtendedDataSheetModels {
	List<DataSheetModel> items() throws IOException;
	DataSheetModel get(Long id) throws IOException;
	boolean contains(DataSheetModel item) throws IOException;
}
