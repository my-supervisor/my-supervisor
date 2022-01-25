package com.supervisor.domain;

import java.util.List;

public interface ActivityTemplateRequest {
	String name();
	List<ActivityTemplateParamRequest> params();
}
