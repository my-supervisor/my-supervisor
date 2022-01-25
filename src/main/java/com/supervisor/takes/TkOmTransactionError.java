package com.supervisor.takes;

import com.supervisor.billing.MobileMoney;
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

public final class TkOmTransactionError extends TkBaseWrap {

	public TkOmTransactionError(final Base base) {
		super(
				base,
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "OM").first();
					
					final MobileMoney mobileMoney = (MobileMoney)method;
					final String token = form.single("token");
					final PaymentReceipt receipt = mobileMoney.payment(token);
					
					final String message;
					final int status = Integer.parseInt(form.single("status"));
					
					switch (status) {
						case -1:
							message = "Paiement échoué";
							break;
						case -2:
							message = "Impossible de récupérer le marchand";
							break;
						case -3:
							message = "Solde du client insuffisant";
							break;
						case -4:
							message = "Erreur Passerelle Orange Money";
							break;
						case -5:
							message = "Echec code OTP";
							break;
						case -6:
							message = "Transaction terminée";
							break;
						case -7:
							message = "Paramètres incorrects (téléphone, code OTP)";
							break;
						case -8:
							message = "Timeout (durée de la session dépassée)";
							break;
						case -9:
							message = "Token invalide";
							break;
						case -10:
							message = "La session a expiré";
							break;
						default:
							message = "Problème survenu non identifié";
							break;
					}
					
					return new RsForward(
								new RsFlash(
									message, 
									Level.INFO
								), 
								String.format("/payment-request/edit?id=%s", receipt.request().id())
						   );
				}
		);
	}
}
