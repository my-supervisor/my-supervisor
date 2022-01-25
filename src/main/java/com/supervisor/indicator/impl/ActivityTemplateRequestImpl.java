package com.supervisor.indicator.impl;

import java.util.List;

import com.supervisor.domain.ActivityTemplateParamRequest;
import com.supervisor.domain.ActivityTemplateRequest;

public final class ActivityTemplateRequestImpl implements ActivityTemplateRequest {

	private final String name;
	private final List<ActivityTemplateParamRequest> params;

	public ActivityTemplateRequestImpl(
			final String name,
			final List<ActivityTemplateParamRequest> params
	) {
		this.name = name;
		this.params = params;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public List<ActivityTemplateParamRequest> params() {
		return params;
	}
}
