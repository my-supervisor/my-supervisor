package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateGenerate extends TkBaseWrap {

	public TkActivityTemplateGenerate(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					ActivityTemplates myItems = module.activityTemplates();
					
					ActivityTemplate itemSaved;
					
					final UUID originId = UUID.fromString(new RqHref.Smart(req).single("activity"));
					Activity origin = module.activities().get(originId);
					itemSaved = myItems.add(origin);
					return new RsForward(
							new RsFlash(
								String.format("Le modèle d'activité %s a été créé avec succès !", itemSaved.name()),
				                Level.FINE
				            ),
				            "/activity/template/edit?id=" + itemSaved.id()
					    );
				}
		);
	}
}
