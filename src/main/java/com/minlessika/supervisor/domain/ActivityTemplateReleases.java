package com.minlessika.supervisor.domain;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainSet;

public interface ActivityTemplateReleases extends DomainSet<ActivityTemplateRelease, ActivityTemplateReleases> {
	ActivityTemplateRelease add(Activity source, String version, String notes) throws IOException;
}
