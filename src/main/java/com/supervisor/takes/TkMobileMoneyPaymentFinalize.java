package com.supervisor.takes;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.billing.PaymentReceiptState;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
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
						            "/home"
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
