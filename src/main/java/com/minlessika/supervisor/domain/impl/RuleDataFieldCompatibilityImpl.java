package com.minlessika.supervisor.domain.impl;

import java.util.List;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldCompatibility;

public final class RuleDataFieldCompatibilityImpl implements DataFieldCompatibility {

	private final DataField source;
	private final List<DataField> targets;
	
	public RuleDataFieldCompatibilityImpl(final DataField source, final List<DataField> targets) {
		this.source = source;
		this.targets = targets;
	}
	
	@Override
	public DataField source() {
		return source;
	}

	@Override
	public List<DataField> targets() {
		return targets;
	}
}
