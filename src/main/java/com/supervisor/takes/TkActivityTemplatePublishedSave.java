package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Profile;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplatePublishedSave extends TkBaseWrap {

	public TkActivityTemplatePublishedSave(final Base base) {
		super(
				base,
				req -> {
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final Supervisor module = new PxSupervisor(base, req);
					
					final UUID templateId = UUID.fromString(form.single("template_id"));
					final UUID profileId = UUID.fromString(form.single("profile_id"));
					final String icon = form.single("icon");
								
					ActivityTemplate template = module.activityTemplates().get(templateId);
					if(new RqUser(base, req).notOwn(template)) {
						throw new IllegalArgumentException("Vous ne pouvez pas publier un modèle dont vous n'êtes pas le concepteur !");
					}
					
					Profile profile = new PxProfiles(base).get(profileId);
					ActivityTemplatePublished itemSaved;

					final OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					if(id.isPresent()) {
						itemSaved = module.activityTemplatesPublished().get(id.get());
					}else
					{			
						itemSaved = module.activityTemplatesPublished().publish(template, icon);
					}
					
					itemSaved.defineIcon(icon);
					itemSaved.changeProfile(profile);
					
					final String msg;
					
					if(id.isPresent())
						msg = "La publication a été mise à jour avec succès !";			
					else
						msg = String.format("Le modèle %s a été publié avec succès !", itemSaved.name());
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
			            "/activity/template"
				    );
				}
		);
	}	
}
