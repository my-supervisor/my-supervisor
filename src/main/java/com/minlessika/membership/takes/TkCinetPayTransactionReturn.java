package com.minlessika.membership.takes;

import com.minlessika.membership.billing.CreditCard;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentReceiptState;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkCinetPayTransactionReturn extends TkBaseWrap {

	public TkCinetPayTransactionReturn(final Base base) {
		super(
				base,
				req -> {
					
					final Membership module = new DmMembership(base, req);									
					final CreditCard method = (CreditCard)module.paymentMethods().where(PaymentMethod::tag, "CINETPAY").first();
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					final String token = form.single("signature");
					
					final PaymentReceipt receipt = method.payments().get(token);						
					final PaymentReceiptState state = method.check(receipt);
					
					final String message;
					final String url;
					
					switch (state) {
						case PENDING:
							message = "Votre paiement est en attente de validation !";
							url = String.format("/payment-request/edit?id=%s", receipt.request().id());
							break;
						case CONFIRMED:
							message = "Votre paiement a été effectué avec succès ! ";
							url = "/home";
							break;
						case CANCELLED:
							message = "Votre paiement a été annulé ! ";
							url = String.format("/payment-request/edit?id=%s", receipt.request().id());
							break;
						default:
							message = "Un problème non identifié est survenu.";
							url = String.format("/payment-request/edit?id=%s", receipt.request().id());
							receipt.cancel();
							break;
					}
					
					return new RsForward(
								new RsFlash(
									message, 
									Level.INFO
								), 
								url
						   );					
				}
		);
	}
}
