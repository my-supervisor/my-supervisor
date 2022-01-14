package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XePlan;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkPlan extends TkBaseWrap {

	public TkPlan(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					Membership module = new DmMembership(base, req);
				    XeSource xePlan = new XePlan(module.plans().items());
				    
					return new RsPage(
							"/com/membership/xsl/plan.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "plan"),
								xePlan
							)
					);
				}
		);
	}	
}
