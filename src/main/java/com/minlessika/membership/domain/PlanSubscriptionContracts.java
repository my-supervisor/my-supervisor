package com.minlessika.membership.domain;

import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface PlanSubscriptionContracts extends DomainSet<PlanSubscriptionContract, PlanSubscriptionContracts> {

	boolean hasValidContract() throws IOException;
	PlanSubscriptionContract currentContract() throws IOException;
	PlanSubscriptionContract subscribe(PurchaseOrder order) throws IOException;
}
