package com.minlessika.membership.takes;

import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.PurchaseOrders;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkPurchaseOrderCancel extends TkBaseWrap {

	public TkPurchaseOrderCancel(Base base) {
		super(
				base, 
				req -> {
					
					final Membership module = new DmMembership(base, req);			
					final PurchaseOrders orders = module.purchaseOrders();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					PurchaseOrder item = orders.get(id);
					item.cancel();
					
					return new RsForward(
						new RsFlash(
			                String.format("Order %s has been successfully cancelled !", item.reference()),
			                Level.FINE
			            ),
						"/purchase-order/edit?id=" + item.id()
				    );
				}
		);
	}
}
