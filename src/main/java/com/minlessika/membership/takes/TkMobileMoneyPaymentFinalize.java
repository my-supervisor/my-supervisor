package com.minlessika.membership.takes;

import com.minlessika.membership.billing.MobileMoney;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkMobileMoneyPaymentFinalize extends TkBaseWrap {

	public TkMobileMoneyPaymentFinalize(final Base base) {
		super(
				base,
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Long requestId = Long.parseLong(form.single("request_id"));
					PaymentRequest paymentRequest = user.paymentRequests().get(requestId);
					
					final Long methodId = Long.parseLong(form.single("method_id"));
					PaymentMethod method = module.paymentMethods().get(methodId);
					
					final MobileMoney mobileMoney = (MobileMoney)method;
					
					String transactionId = form.single("transaction_id");
					PaymentReceipt receipt = mobileMoney.payment(transactionId);
					if(receipt.state() == PaymentReceiptState.CONFIRMED) {		
						return new RsForward(
								new RsFlash(
										"Payment has been successfully performed !",
						                Level.FINE
						            ),
						            String.format("%s/home", base.appInfo().moduleLinks().get(paymentRequest.application().module()))
							    );
					}else {
						return new RsForward(
								new RsFlash(
										"Payment has been cancelled !",
						                Level.INFO
						            ),
						            "/payment-request/edit?id=" + paymentRequest.id()
							    );
					}
				}
		);
	}
}
