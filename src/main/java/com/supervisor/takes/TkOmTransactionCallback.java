
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
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsEmpty;

import java.util.Map;

public final class TkOmTransactionCallback extends TkBaseWrap {

	public TkOmTransactionCallback(final Base base) { 
		super(
				base,
				req -> {
					RecordSet<User> source = base.select(User.class);
					User user = new DmUser(source.get(User.ADMIN_ID)); // prendre l'utilisateur Minlessika
					
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
