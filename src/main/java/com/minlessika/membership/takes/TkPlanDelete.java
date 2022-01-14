package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.Plans;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkPlanDelete extends TkBaseWrap {

	public TkPlanDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Plans plans = module.plans();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					Plan item = plans.get(id);
					String name = item.name();
					plans.remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("Le plan < %s > a été supprimé avec succès !", name),
			                Level.FINE
			            ),
						"/admin/plan"
				    );
				}
		);
	}
}
