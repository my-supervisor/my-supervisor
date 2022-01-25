package com.supervisor.indicator.impl;

import java.util.Map;

import com.supervisor.indicator.ReplacingOfModel;

public final class ReplacingOfModelImpl implements ReplacingOfModel {

	private final Long ruleId;
	private final Long replacingModelId;
	private final Map<String, String> fieldMappings;
	
	public ReplacingOfModelImpl(final Long ruleId, final Long replacingModelId, final Map<String, String> fieldMappings) {
		this.ruleId = ruleId;
		this.replacingModelId = replacingModelId;
		this.fieldMappings = fieldMappings;
	}
	
	@Override
	public Long ruleId() {
		return ruleId;
	}

	@Override
	public Long replacingModelId() {
		return replacingModelId;
	}

	@Override
	public Map<String, String> fieldMappings() {
		return fieldMappings;
	}
}
