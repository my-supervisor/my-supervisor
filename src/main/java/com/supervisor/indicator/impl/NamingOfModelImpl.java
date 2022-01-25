package com.supervisor.indicator.impl;

import com.supervisor.indicator.NamingOfModel;

public final class NamingOfModelImpl implements NamingOfModel {

	private final Long ruleId;
	private final String newModelName;
	
	public NamingOfModelImpl(final Long ruleId, final String newModelName) {
		this.ruleId = ruleId;
		this.newModelName = newModelName;
	}
	
	@Override
	public Long ruleId() {
		return ruleId;
	}

	@Override
	public String newModelName() {
		return newModelName;
	}
}
