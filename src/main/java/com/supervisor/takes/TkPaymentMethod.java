package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XePaymentMethod;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkPaymentMethod extends TkBaseWrap {

	public TkPaymentMethod(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					Membership module = new DmMembership(base, req);
				    XeSource xeMethods = new XePaymentMethod(module.paymentMethods().items());
				    
					return new RsPage(
                            "/xsl/payment_method.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "payment-method"),
								xeMethods
							)
					);
				}
		);
	}	
}
