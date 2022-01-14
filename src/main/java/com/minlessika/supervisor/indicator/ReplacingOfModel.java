package com.minlessika.supervisor.indicator;

import java.util.Map;

public interface ReplacingOfModel {
	Long ruleId();
	Long replacingModelId();
	Map<String, String> fieldMappings();
}
