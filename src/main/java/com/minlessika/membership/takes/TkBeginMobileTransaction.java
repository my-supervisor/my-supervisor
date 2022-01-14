package com.minlessika.membership.takes;

import com.minlessika.membership.billing.MobileMoney;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeMobileMoneyStatusJson;
import com.minlessika.rq.RqJson;
import com.minlessika.rq.RqJsonBase;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.Request;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rs.RsJson;

import javax.json.JsonObject;

public final class TkBeginMobileTransaction extends TkBaseWrap {

	public TkBeginMobileTransaction(final Base base) {
		super(
				base,
				req -> {
					try {
						
						final Membership module = new DmMembership(base, req);
						final User user = module.user();
						
						final Request gReq = new RqGreedy(req);
						
						final RqJson rqJson = new RqJsonBase(gReq);					
						JsonObject remplacement = rqJson.payload().asJsonObject();
						final String number = remplacement.getString("number");
						
						Long methodId = Long.parseLong(new RqHref.Smart(gReq).single("method"));
						Long paymentRequestId = Long.parseLong(new RqHref.Smart(gReq).single("request"));
						PaymentMethod method = module.paymentMethods().get(methodId);
						PaymentRequest paymentRequest = user.paymentRequests().get(paymentRequestId);
						
						final MobileMoney mobileMoney = (MobileMoney)method;
						PaymentReceipt receipt = mobileMoney.request(number, paymentRequest);
						
						return new RsJson(
								new XeMobileMoneyStatusJson("200", "Une demande de paiement vous a été envoyée. Veuillez la confirmer S.V.P.", receipt.metadata().get("TransactionId1"))
						);
					} catch (IllegalArgumentException e) {
						return new RsJson(
								new XeMobileMoneyStatusJson("400", e.getLocalizedMessage())
						);
					} catch (Exception e) {
						return new RsJson(
								new XeMobileMoneyStatusJson("500", e.getLocalizedMessage())
						);
					}
				}
		);
	}
}
