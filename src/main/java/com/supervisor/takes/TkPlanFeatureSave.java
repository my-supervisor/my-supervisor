package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.PlanFeature;
import com.supervisor.domain.PlanFeatures;
import com.supervisor.domain.Plans;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.UUID;
import java.util.logging.Level;

public final class TkPlanFeatureSave extends TkBaseWrap {

	public TkPlanFeatureSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					final Plans plans = module.plans();
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					UUID planId = UUID.fromString(form.single("plan_id"));
					final PlanFeatures features = plans.get(planId).features();
					
					OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					
					final String name = form.single("name");
					final String description = form.single("description");
					final int order = Integer.parseInt(form.single("order"));
					final boolean basic = Boolean.parseBoolean(form.single("basic"));
					final boolean enabled = Boolean.parseBoolean(form.single("enabled"));		
					
					final PlanFeature item;
					final String msg;
					if(id.isPresent()) {
						item = features.get(id.value());
						msg = String.format("La fonctionnalité %s a été modifiée avec succès !", item.name());
					}else {
						item = features.add(name);
						
						msg = String.format("La fonctionnalité %s a été créée avec succès !", item.name());
					}
					
					item.update(name, enabled, basic);
					item.describe(description); 
					item.organize(order);
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/admin/plan/edit?id=" + planId
					    );
				}
		);
	}
}
