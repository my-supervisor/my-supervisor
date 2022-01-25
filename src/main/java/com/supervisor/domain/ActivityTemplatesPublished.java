package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainSet;

public interface ActivityTemplatesPublished extends DomainSet<ActivityTemplatePublished, ActivityTemplatesPublished> {
	ActivityTemplatePublished publish(ActivityTemplate template, String icon) throws IOException;
}
