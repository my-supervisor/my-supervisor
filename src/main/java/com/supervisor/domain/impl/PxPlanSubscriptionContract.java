package com.supervisor.domain.impl;

import com.supervisor.billing.SubscriptionContract;
import com.supervisor.billing.impl.PxSubscriptionContract;
import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanSubscriptionContract;
import com.supervisor.sdk.datasource.Record;

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
