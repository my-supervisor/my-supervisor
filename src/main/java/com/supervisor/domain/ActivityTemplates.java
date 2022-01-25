package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface ActivityTemplates extends DomainSet<ActivityTemplate, ActivityTemplates> {
	ActivityTemplate add(Activity activity) throws IOException;
}
