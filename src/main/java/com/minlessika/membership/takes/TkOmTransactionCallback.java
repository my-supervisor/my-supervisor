
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
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsEmpty;

import java.util.Map;

public final class TkOmTransactionCallback extends TkBaseWrap {

	public TkOmTransactionCallback(final Base base) { 
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(1L)); // prendre l'utilisateur Minlessika
					
					final Membership membership = new DmMembership(base, user);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));					
					
					PaymentMethod method = membership.paymentMethods().where(PaymentMethod::tag, "OM").first();
					
					final MobileMoney mobileMoney = (MobileMoney)method;
					final String token = form.single("token");
					PaymentReceipt receipt = mobileMoney.payment(token);
					
					final Map<String, String> metadata = receipt.metadata();
					metadata.put("clientid", form.single("clientid"));
					metadata.put("cname", form.single("cname"));					
					metadata.put("date", form.single("date"));
					metadata.put("time", form.single("time"));
					metadata.put("ipaddr", form.single("ipaddr"));
					
					final int status = Integer.parseInt(form.single("status"));
					if(status == 0) { // si confirmé							
						metadata.put("payid", form.single("payid"));
						mobileMoney.complete(receipt);
					} else {
						metadata.put("error", form.single("error"));
						receipt.cancel();
					}
					
					receipt.updateMetadata(metadata);
					
					return new RsEmpty();
				}
		);
	}
}
