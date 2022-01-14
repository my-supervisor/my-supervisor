package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PlanFeatures extends DomainSet<PlanFeature, PlanFeatures> {
	PlanFeature add(String name) throws IOException;
}
