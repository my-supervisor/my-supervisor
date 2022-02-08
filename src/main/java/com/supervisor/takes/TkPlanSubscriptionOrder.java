package com.supervisor.takes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.User;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkPlanSubscriptionOrder extends TkBaseWrap {

	public TkPlanSubscriptionOrder(Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Membership membership = module.membership();
					final User user = new RqUser(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final UUID planId = UUID.fromString(form.single("plan_id"));
					final int delay = Integer.parseInt(form.single("delay"));		
							
					Plan plan = membership.plans().get(planId);
					PurchaseOrder order = membership.purchaseOrders().add(user, StringUtils.EMPTY);
					
					order.lines().add(plan, delay);
					order.taxes().apply();
					
					order.calculate();
					
					Map<String, String> metadata = new HashMap<>();
					metadata.put(PurchaseOrder.class.getSimpleName(), order.id().toString());
					
					PaymentRequest request = order.requests()
							                      .add(
														String.format("Request payment for Plan Order %s", order.reference()), 
														order.totalAmount(), 
														String.format("We request an payment for Order than your send to us for Plan %s with %s months. Please, use a payment method available here to achieve it.", plan.name(), order.lines().first().quantity()), 
														metadata
												  ); 
					
					return new RsForward(
			            String.format("/payment-request/edit?id=%s", request.id())
					);
				}
		);
	}
}
