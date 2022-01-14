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

public final class TkActivityTemplatePublishedDowload extends TkBaseWrap {

	public TkActivityTemplatePublishedDowload(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));					
					ActivityTemplatePublished template = module.activityTemplatesPublished().get(id);
					
					if(!new RqUser(base, req).currentProfile().isUpperOrEqualTo(template.profile()))
						throw new IllegalArgumentException(String.format("Vous avez besoin d'un profil supérieur ou égal au profil <%s> pour vous abonner à ce modèle !", template.profile().name()));
					
					template.subscribe();
					
					return new RsForward(
						new RsFlash(
			                "Votre abonnement au modèle a été effectué avec succès !", 
			                Level.FINE
			            ),
						"/activity/template"
				    );
				}
		);
	}
}
