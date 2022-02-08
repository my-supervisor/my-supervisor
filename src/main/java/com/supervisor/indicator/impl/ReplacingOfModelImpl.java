package com.supervisor.indicator.impl;

import java.util.Map;
import java.util.UUID;

import com.supervisor.indicator.ReplacingOfModel;

public final class ReplacingOfModelImpl implements ReplacingOfModel {

	private final UUID ruleId;
	private final UUID replacingModelId;
	private final Map<String, String> fieldMappings;
	
	public ReplacingOfModelImpl(final UUID ruleId, final UUID replacingModelId, final Map<String, String> fieldMappings) {
		this.ruleId = ruleId;
		this.replacingModelId = replacingModelId;
		this.fieldMappings = fieldMappings;
	}
	
	@Override
	public UUID ruleId() {
		return ruleId;
	}

	@Override
	public UUID replacingModelId() {
		return replacingModelId;
	}

	@Override
	public Map<String, String> fieldMappings() {
		return fieldMappings;
	}
}
