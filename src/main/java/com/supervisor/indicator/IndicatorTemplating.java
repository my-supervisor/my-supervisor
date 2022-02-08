package com.supervisor.indicator;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface IndicatorTemplating {
	IndicatorTemplate process() throws IOException;
	Map<UUID, UUID> aggregatedModelMapping();
	Map<UUID, UUID> dataSheetModelMapping();
}
