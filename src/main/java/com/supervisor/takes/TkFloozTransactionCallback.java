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

public final class TkFloozTransactionCallback extends TkBaseWrap {

	public TkFloozTransactionCallback(final Base base) {
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(User.ADMIN_ID)); // prendre l'utilisateur Minlessika
					
					final Membership module = new DmMembership(base, user);
					
					final Smart href = new Smart(new RqGreedy(req));
					final int status = Integer.parseInt(href.single("status"));
					
					PaymentMethod method = module.paymentMethods().where(PaymentMethod::tag, "FLOOZ").first();
					
					final MobileMoney mobileMoney = (MobileMoney)method;
					final String transactionId = href.single("id");
					PaymentReceipt receipt = mobileMoney.payment(transactionId);
					
					if(status == 3) // si confirmé
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
