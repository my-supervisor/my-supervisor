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

public final class TkFloozTransactionCallback extends TkBaseWrap {

	public TkFloozTransactionCallback(final Base base) {
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(1L)); // prendre l'utilisateur Minlessika
					
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
