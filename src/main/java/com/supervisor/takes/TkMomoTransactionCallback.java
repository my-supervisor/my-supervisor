package com.supervisor.takes;

import com.supervisor.billing.MobileMoney;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentReceipt;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.DmUser;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsEmpty;

public final class TkMomoTransactionCallback extends TkBaseWrap {

	public TkMomoTransactionCallback(final Base base) {
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(User.ADMIN_ID)); // prendre l'utilisateur Minlessika
					
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
