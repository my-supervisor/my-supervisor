package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;

public interface Resource extends Recordable {
	String name();
	ResourceType type();
}
