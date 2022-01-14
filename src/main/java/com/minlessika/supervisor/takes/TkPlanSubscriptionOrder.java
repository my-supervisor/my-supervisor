package com.minlessika.supervisor.takes;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkPlanSubscriptionOrder extends TkBaseWrap {

	public TkPlanSubscriptionOrder(Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Membership membership = module.membership();
					final User user = new RqUser(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Long planId = Long.parseLong(form.single("plan_id"));
					final int delay = Integer.parseInt(form.single("delay"));		
							
					Plan plan = membership.plans().get(planId);
					PurchaseOrder order = membership.purchaseOrders().add(user, StringUtils.EMPTY);
					
					order.lines().add(plan, delay);
					order.taxes().apply();
					
					order.calculate();
					
					Map<String, String> metadata = new HashMap<>();
					metadata.put(Application.class.getSimpleName(), user.applications().current().id().toString());
					metadata.put(PurchaseOrder.class.getSimpleName(), order.id().toString());
					
					PaymentRequest request = order.requests()
							                      .add(
														String.format("Request payment for Plan Order %s", order.reference()), 
														order.totalAmount(), 
														String.format("We request an payment for Order than your send to us for Plan %s with %s months. Please, use a payment method available here to achieve it.", plan.name(), order.lines().first().quantity()), 
														metadata
												  ); 
					
					return new RsForward(
			            String.format("%s/payment-request/edit?id=%s", base.appInfo().minlessikaDomain(), request.id())
					);
				}
		);
	}
}
