package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface ActivityParams extends DomainSet<ActivityParam, ActivityParams> {
	ActivityParam put(ParamDataField param, String value) throws IOException;
}
