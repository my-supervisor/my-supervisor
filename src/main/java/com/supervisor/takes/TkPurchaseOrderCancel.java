package com.supervisor.takes;

import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.PurchaseOrders;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
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
