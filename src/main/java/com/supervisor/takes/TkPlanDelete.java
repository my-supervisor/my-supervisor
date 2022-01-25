package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.Plans;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
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
