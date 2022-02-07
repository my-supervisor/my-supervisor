package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.interaction.Interaction;

public final class TkInteractionActivate extends TkBaseWrap {

	public TkInteractionActivate(Base base) {
		super(
				base,
				req -> {
					new RqUser(base, req).profile().validateAccessibility("INTERACTE_ACTIVITIES");
					
					final Supervisor module = new PxSupervisor(base, req);
					final Activities myActivities = module.activities();
					
					final Smart href = new RqHref.Smart(req);
					final Long receiverId = Long.parseLong(href.single("receiver"));
					final Long actorId = Long.parseLong(href.single("actor"));
					final boolean activate = Boolean.parseBoolean(href.single("activate"));
					
					final Activity actor = myActivities.get(actorId);
					final Activity receiver = myActivities.get(receiverId);
					
					final Interaction interaction = receiver.interactions().getWith(actor);
					final String msg;
					
					if(activate) {
						interaction.activate(true);
						msg = String.format("L'interaction avec l'activité %s a été activée avec succès !", interaction.actor().name());
					} else {
						interaction.activate(false);
						msg = String.format("L'interaction avec l'activité %s a été désactivée avec succès !", interaction.actor().name());
					}
							
					return new RsForward(
							new RsFlash(
								msg,
				                Level.FINE
				            ),
				            String.format("/activity/interaction?activity=%s", receiver.id())
					    );
				}
		);
	}
}
