package com.supervisor.domain;

import java.util.UUID;

public interface ActivityTemplateParamRequest {
	UUID modelId();
	String code();
	String value();
}
