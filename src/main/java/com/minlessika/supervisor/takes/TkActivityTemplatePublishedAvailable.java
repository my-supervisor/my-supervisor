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

public final class TkActivityTemplatePublishedAvailable extends TkBaseWrap {

	public TkActivityTemplatePublishedAvailable(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					ActivityTemplatePublished itemSaved = module.activityTemplatesPublished().get(id);
					
					if(new RqUser(base, req).notOwn(itemSaved)) {
						throw new IllegalArgumentException("Vous ne pouvez pas rendre disponible un modèle dont vous n'êtes pas le concepteur !");
					}
					
					itemSaved.makeAvailable();

					return new RsForward(
						new RsFlash(
							"Le modèle a été publié de nouveau !",
			                Level.FINE
			            ),
						"/activity/template"
				    );
				}
		);
	}
}
