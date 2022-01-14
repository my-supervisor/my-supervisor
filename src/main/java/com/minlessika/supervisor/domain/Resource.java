package com.minlessika.supervisor.domain;

import com.minlessika.sdk.datasource.Recordable;

public interface Resource extends Recordable {
	String name();
	ResourceType type();
}
