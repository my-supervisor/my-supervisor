package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XePlan;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
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
                            "/xsl/plan.xsl",
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
