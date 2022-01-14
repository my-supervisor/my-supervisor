package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.membership.domain.Plans;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkPlanFeatureDelete extends TkBaseWrap {

	public TkPlanFeatureDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Plans plans = module.plans();
					
					final Long planId = Long.parseLong(new RqHref.Smart(req).single("plan"));
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					Plan plan = plans.get(planId);
					PlanFeature item = plan.features().get(id);
					String name = item.name();
					plan.features().remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("La fonctionnalité du plan < %s > a été supprimé avec succès !", name),
			                Level.FINE
			            ),
						"/admin/plan/edit?id=" + planId
				    );
				}
		);
	}
}
