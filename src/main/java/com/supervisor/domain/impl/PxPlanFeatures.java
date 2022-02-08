package com.supervisor.domain.impl;

import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanFeature;
import com.supervisor.domain.PlanFeatures;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;
import java.util.UUID;

public final class PxPlanFeatures extends DomainRecordables<PlanFeature, PlanFeatures> implements PlanFeatures {

	private final Plan plan;
	
	public PxPlanFeatures(final Plan plan) throws IOException {
		this(plan.listOf(PlanFeature.class), plan);
	}
	
	public PxPlanFeatures(final RecordSet<PlanFeature> source, final Plan plan) throws IOException {
		super(PxPlanFeature.class, source);
		
		this.plan = plan;
		this.source = source.where(PlanFeature::plan, plan.id())
						    .orderBy(PlanFeature::order);
	}

	@Override
	protected PlanFeatures domainSetOf(final RecordSet<PlanFeature> source) throws IOException {
		return new PxPlanFeatures(source, plan);
	}
	
	@Override
	protected PlanFeature domainOf(final UUID id) throws IOException {
		return new PxPlanFeature(source.get(id), plan);					
	}

	@Override
	public PlanFeature add(String name) throws IOException {
		
		source.isRequired(PlanFeature::name, name);
		
		Record<PlanFeature> record = source.entryOf(PlanFeature::name, name)
							                .entryOf(PlanFeature::plan, plan.id())
							                .entryOf(PlanFeature::basic, false)
							                .entryOf(PlanFeature::enabled, true)
							                .entryOf(PlanFeature::order, isEmpty() ? 1 : last().order() + 1)
							                .add();

		return domainOf(record);
	}
}
