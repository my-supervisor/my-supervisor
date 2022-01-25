package com.supervisor.takes;

import com.supervisor.billing.PaymentRequest;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkPaymentRequestCancel extends TkBaseWrap {

	public TkPaymentRequestCancel(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);
					final User user = module.user();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					PaymentRequest item = user.paymentRequests().get(id);
					item.cancel();
					
					return new RsForward(
						new RsFlash(
			                String.format("Payment request %s has been successfully cancelled !", item.reference()),
			                Level.FINE
			            ),
						"/payment-request/edit?id=" + item.id()
				    );
				}
		);
	}
}
