package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.ActivityTemplatePublished;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplatePublishedObsolete extends TkBaseWrap {

	public TkActivityTemplatePublishedObsolete(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					ActivityTemplatePublished itemSaved = module.activityTemplatesPublished().get(id);
								
					if(new RqUser(base, req).notOwn(itemSaved)) {
						throw new IllegalArgumentException("Vous ne pouvez pas rendre obsolète un modèle dont vous n'êtes pas le concepteur !");
					}
				
					itemSaved.makeObsolete();

					return new RsForward(
						new RsFlash(
							"La publication a été rendue obsolète !",
			                Level.FINE
			            ),
						"/activity/template"
				    );
				}
		);
	}
}
