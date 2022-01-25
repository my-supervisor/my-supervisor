package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XeTax;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
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
                            "/xsl/tax.xsl",
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
