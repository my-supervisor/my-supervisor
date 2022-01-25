package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PlanFeatures extends DomainSet<PlanFeature, PlanFeatures> {
	PlanFeature add(String name) throws IOException;
}
