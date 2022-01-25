package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeSource;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeInteraction;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkInteraction extends TkBaseWrap {

	public TkInteraction(final Base base) {
		super(
				base, 
				req -> {
					
					new RqUser(base, req).currentProfile().validateAccessibility("INTERACTE_ACTIVITIES");					
					
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long activityId = Long.parseLong(new RqHref.Smart(req).single("activity"));
					final Activities activities = module.activities();
					final Activity activity = activities.get(activityId);
					
					final XeSource xeActivity = new XeActivity(activity);					
					XeSource xeSupervisor = new XeSupervisor(module);
					XeSource xeInteractions = new XeInteraction(activity.interactions());
					
					return new RsPage(
							"/xsl/interaction.xsl",
							req, 
							base,
							()-> new Sticky<>(
									xeActivity,
									xeSupervisor,
									xeInteractions
							)
					);
				}
		);
	}	
}
