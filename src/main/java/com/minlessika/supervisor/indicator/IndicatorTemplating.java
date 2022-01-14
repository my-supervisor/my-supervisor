package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.util.Map;

public interface IndicatorTemplating {
	IndicatorTemplate process() throws IOException;
	Map<Long, Long> aggregatedModelMapping();
	Map<Long, Long> dataSheetModelMapping();
}
