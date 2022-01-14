package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeTax;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkTax extends TkBaseWrap {

	public TkTax(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					Membership module = new DmMembership(base, req);
				    XeSource xeTax = new XeTax(module.taxes().items());
				    
					return new RsPage(
							"/com/membership/xsl/tax.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "tax"),
								xeTax
							)
					);
				}
		);
	}	
}
