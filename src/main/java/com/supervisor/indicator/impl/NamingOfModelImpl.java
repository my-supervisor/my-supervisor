package com.supervisor.indicator.impl;

import com.supervisor.indicator.NamingOfModel;

import java.util.UUID;

public final class NamingOfModelImpl implements NamingOfModel {

	private final UUID ruleId;
	private final String newModelName;
	
	public NamingOfModelImpl(final UUID ruleId, final String newModelName) {
		this.ruleId = ruleId;
		this.newModelName = newModelName;
	}
	
	@Override
	public UUID ruleId() {
		return ruleId;
	}

	@Override
	public String newModelName() {
		return newModelName;
	}
}
