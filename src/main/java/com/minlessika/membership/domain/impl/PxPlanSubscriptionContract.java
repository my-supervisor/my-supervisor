package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.SubscriptionContract;
import com.minlessika.membership.billing.impl.PxSubscriptionContract;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanSubscriptionContract;
import com.minlessika.sdk.datasource.Record;

import java.io.IOException;

public final class PxPlanSubscriptionContract extends PxSubscriptionContract implements PlanSubscriptionContract {

	private final Record<PlanSubscriptionContract> record;
	
	public PxPlanSubscriptionContract(Record<PlanSubscriptionContract> record) throws IOException {
		super(record.of(SubscriptionContract.class, record.id()));
		
		this.record = record;
	}

	@Override
	public Plan plan() throws IOException {
		Record<Plan> rec = record.of(PlanSubscriptionContract::plan);
		return new PxPlan(rec);
	}

}
