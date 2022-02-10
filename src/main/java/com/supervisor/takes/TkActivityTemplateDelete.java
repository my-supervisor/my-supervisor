package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateDelete extends TkBaseWrap {

	public TkActivityTemplateDelete(Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final ActivityTemplates myActivities = module.activityTemplates();
					
					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(!id.isPresent())
						throw new IllegalArgumentException("Cet élément n'existe pas !");
					
					ActivityTemplate item = myActivities.get(id.get());
					
					if(new RqUser(base, req).notOwn(item)) {
						throw new IllegalArgumentException("Vous ne pouvez pas supprimer une activité partagée !");
					}
					
					String name = item.name();
					myActivities.remove(item);
								
					return new RsForward(
						new RsFlash(
			                String.format("Le modèle d'activité %s a été supprimée avec succès !", name),
			                Level.FINE
			            ),
			            "/activity/template"
				    );
				}
		);
	}	
}
