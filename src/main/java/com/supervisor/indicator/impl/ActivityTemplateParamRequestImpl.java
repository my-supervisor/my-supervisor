package com.supervisor.indicator.impl;

import com.supervisor.domain.ActivityTemplateParamRequest;

public final class ActivityTemplateParamRequestImpl implements ActivityTemplateParamRequest {

	private final Long modelId;
	private final String code;
	private final String value;

	public ActivityTemplateParamRequestImpl(
			final Long modelId,
			final String code,
			final String value
	) {
		this.modelId = modelId;
		this.code = code;
		this.value = value;
	}
	
	@Override
	public Long modelId() {
		return modelId;
	}
	
	@Override
	public String code() {
		return code;
	}
	
	@Override
	public String value() {
		return value;
	}
}
