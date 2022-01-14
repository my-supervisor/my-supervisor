package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplates;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateGenerate extends TkBaseWrap {

	public TkActivityTemplateGenerate(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					ActivityTemplates myItems = module.activityTemplates();
					
					ActivityTemplate itemSaved;
					
					final Long originId = Long.parseLong(new RqHref.Smart(req).single("activity"));
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
