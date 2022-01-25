package com.supervisor.takes;

import com.supervisor.billing.CreditCard;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
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
