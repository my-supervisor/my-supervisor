package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ActivityTemplatesPublished extends DomainSet<ActivityTemplatePublished, ActivityTemplatesPublished> {
	ActivityTemplatePublished publish(ActivityTemplate template, String icon) throws IOException;
}
