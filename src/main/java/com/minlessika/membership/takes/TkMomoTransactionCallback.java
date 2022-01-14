package com.minlessika.membership.takes;

import com.minlessika.membership.billing.MobileMoney;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsEmpty;

public final class TkMomoTransactionCallback extends TkBaseWrap {

	public TkMomoTransactionCallback(final Base base) {
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(1L)); // prendre l'utilisateur Minlessika
					
					final Membership membership = new DmMembership(base, user);
					
					final Smart href = new Smart(new RqGreedy(req));
					final String status = href.single("ResponseCode");
					
					PaymentMethod method = membership.paymentMethods().where(PaymentMethod::tag, "MOMO").first();
					
					final MobileMoney mobileMoney = (MobileMoney)method;
					final String transactionId = href.single("BillMapTransactionId");
					PaymentReceipt receipt = mobileMoney.payment(transactionId);
					
					if(status.equals("01")) // si confirmé
					{			
						mobileMoney.complete(receipt);
					} else {
						receipt.cancel();
					}
					
					return new RsEmpty();
				}
		);
	}
}
