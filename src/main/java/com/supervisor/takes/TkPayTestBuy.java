
package com.supervisor.takes;

import com.supervisor.billing.Order;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public final class TkPayTestBuy extends TkBaseWrap {

	public TkPayTestBuy(final Base base) { 
		super(
				base,
				req -> {
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("request"));
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
					            "/home"
						    );
				}
		);
	}
}
