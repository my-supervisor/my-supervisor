package com.minlessika.membership.takes;

import com.minlessika.membership.billing.CreditCard;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkCinetPayCancelTransaction extends TkBaseWrap {

	public TkCinetPayCancelTransaction(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					CreditCard method = (CreditCard)module.paymentMethods().where(PaymentMethod::tag, "CINETPAY").first();
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					final String token = form.single("signature");
					
					final PaymentReceipt receipt = method.payments().get(token);
					receipt.cancel();
					
					return new RsForward(
							new RsFlash(
								"Payment has been cancelled !", 
								Level.INFO
							), 
							String.format("/payment-request/edit?id=%s", receipt.request().id())
					   );					
				}
		);
	}
}
