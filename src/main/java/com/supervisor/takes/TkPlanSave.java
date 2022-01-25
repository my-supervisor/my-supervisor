package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.Plans;
import com.supervisor.domain.Profile;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkPlanSave extends TkBaseWrap {

	public TkPlanSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					final Plans plans = module.plans();
					
					Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					
					final int order = Integer.parseInt(form.single("order"));
					final double price = Double.parseDouble(form.single("price"));
					final boolean preferred = Boolean.parseBoolean(form.single("preferred"));
					final boolean enabled = Boolean.parseBoolean(form.single("enabled"));		
					
					final Plan item;
					final String msg;
					if(id > 0) {	
						item = plans.get(id);			
						msg = String.format("Le plan %s a été modifié avec succès !", item.name());
					}else {
						final String reference = form.single("reference");
						final Long profileId = Long.parseLong(form.single("profile_id"));
						final Profile profile = new PxProfiles(base, "supervisor").get(profileId);
						
						item = plans.add(reference, profile, price);
						
						msg = String.format("Le plan %s a été créé avec succès !", item.name());
					}
					
					item.evaluate(price);
					item.organize(order);	
					item.prefer(preferred);
					item.enable(enabled); 
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/admin/plan"
					    );
				}
		);
	}
}
