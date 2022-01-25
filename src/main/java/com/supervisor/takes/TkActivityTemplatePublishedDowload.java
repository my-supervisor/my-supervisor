package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

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
