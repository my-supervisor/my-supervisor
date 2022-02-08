package com.supervisor.indicator;

import java.util.Map;
import java.util.UUID;

public interface ReplacingOfModel {
	UUID ruleId();
	UUID replacingModelId();
	Map<String, String> fieldMappings();
}
