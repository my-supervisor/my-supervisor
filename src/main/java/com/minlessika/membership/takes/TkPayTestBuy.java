
package com.minlessika.membership.takes;

import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.HashMap;
import java.util.logging.Level;

public final class TkPayTestBuy extends TkBaseWrap {

	public TkPayTestBuy(final Base base) { 
		super(
				base,
				req -> {
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("request"));					
					final PaymentRequest payRequest = user.paymentRequests().get(id);
					final PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "PAY_TEST").first();
					
					// create a PENDING receipt
	            	final Order order = payRequest.order();
	            	final String object = String.format("Paiement test du bon de commande N° %s", order.reference());
	            	final PaymentReceipt receipt = order.receipts().add(method, payRequest, object, new HashMap<>());
					
	            	// complete receipt
	            	method.complete(receipt);
					
	            	return new RsForward(
								new RsFlash(
									"Payment has been successfully performed !",
					                Level.FINE
					            ),
					            String.format("%s/home", base.appInfo().moduleLinks().get(payRequest.application().module()))
						    );
				}
		);
	}
}
