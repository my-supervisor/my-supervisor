package com.supervisor.domain;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ExtendedDataSheetModels {
	List<DataSheetModel> items() throws IOException;
	DataSheetModel get(UUID id) throws IOException;
	boolean contains(DataSheetModel item) throws IOException;
}
