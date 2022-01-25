package com.supervisor.domain;

import com.supervisor.billing.PurchaseOrder;
import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PlanSubscriptionContracts extends DomainSet<PlanSubscriptionContract, PlanSubscriptionContracts> {

	boolean hasValidContract() throws IOException;
	PlanSubscriptionContract currentContract() throws IOException;
	PlanSubscriptionContract subscribe(PurchaseOrder order) throws IOException;
}
