package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XePaymentMethod;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
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
