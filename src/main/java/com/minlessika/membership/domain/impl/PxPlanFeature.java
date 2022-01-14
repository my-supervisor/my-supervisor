package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;

public final class PxPlanFeature extends DomainRecordable<PlanFeature> implements PlanFeature {

	private final Plan plan;
	
	public PxPlanFeature(Record<PlanFeature> record, final Plan plan) throws IOException {
		super(record);
		
		this.plan = plan;
	}

	@Override
	public Plan plan() throws IOException {
		return plan;
	}

	@Override
	public int order() throws IOException {
		return record.valueOf(PlanFeature::order);
	}

	@Override
	public boolean basic() throws IOException {
		return record.valueOf(PlanFeature::basic);
	}

	@Override
	public boolean enabled() throws IOException {
		return record.valueOf(PlanFeature::enabled);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(PlanFeature::name);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(PlanFeature::description);
	}

	@Override
	public void update(String name, boolean enabled, boolean basic) throws IOException {
		
		record.isRequired(PlanFeature::name, name);
		
		record.entryOf(PlanFeature::name, name)
			  .entryOf(PlanFeature::enabled, enabled)
			  .entryOf(PlanFeature::basic, basic)
  		  	  .update();
	}

	@Override
	public void organize(int order) throws IOException {
		
		record.entryOf(PlanFeature::order, order)
  	  		  .update();
	}

	@Override
	public void describe(String description) throws IOException {
		
		record.entryOf(PlanFeature::description, description)
  		      .update();
	}

}
