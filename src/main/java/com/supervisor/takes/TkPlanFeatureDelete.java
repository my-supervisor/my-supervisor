package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanFeature;
import com.supervisor.domain.Plans;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.UUID;
import java.util.logging.Level;

public final class TkPlanFeatureDelete extends TkBaseWrap {

	public TkPlanFeatureDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Plans plans = module.plans();
					
					final UUID planId = UUID.fromString(new RqHref.Smart(req).single("plan"));
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));

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
