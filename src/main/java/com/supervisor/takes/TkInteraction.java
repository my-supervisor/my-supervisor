package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeInteraction;
import com.supervisor.xe.XeSupervisor;

import java.util.UUID;

public final class TkInteraction extends TkBaseWrap {

	public TkInteraction(final Base base) {
		super(
				base, 
				req -> {
					
					new RqUser(base, req).profile().validateAccessibility("INTERACTE_ACTIVITIES");
					
					final Supervisor module = new PxSupervisor(base, req);
					
					final UUID activityId = UUID.fromString(new RqHref.Smart(req).single("activity"));
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
