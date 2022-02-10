package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateRelease;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateReleaseSave extends TkBaseWrap {

	public TkActivityTemplateReleaseSave(Base base) {
		super(
				base,
				req -> {
					final RqHref.Smart href = new RqHref.Smart(req);	
					final Supervisor module = new PxSupervisor(base, req);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));							
					final String version = form.single("version");
					final String notes = form.single("notes");
						
					final UUID templateId = UUID.fromString(form.single("release_template_id"));
					final ActivityTemplate template = module.activityTemplates().get(templateId);

					final ActivityTemplateRelease release;
					final OptUUID id = new OptUUID(href.single("id", "0"));
					if(id.isPresent()) {
						release = template.releases().get(id.get());
						release.update(version, notes); 
					}else
					{
						final Activity activity = module.activities().get(UUID.fromString(form.single("release_activity_src_id")));
						release = template.releases().add(activity, version, notes);
					}
							
					final String msg;
					if(id.isPresent())
						msg = String.format("La release %s a été modifiée avec succès !", release.version());
					else
						msg = String.format("La release %s a été créée avec succès !", release.version());
					
					return new RsForward(
							new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/activity/template/edit?id=" + template.id()
					);
				}
		);
	}
}
