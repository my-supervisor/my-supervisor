package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateRelease;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityUpdateApply extends TkBaseWrap {

	public TkActivityUpdateApply(Base base) {
		super(
				base,
				req -> {
					final RqHref.Smart href = new RqHref.Smart(req);	
					final Supervisor module = new PxSupervisor(base, req);
											
					final Long templateId = Long.parseLong(href.single("template"));
					final Long activityId = Long.parseLong(href.single("activity"));
					final Long releaseId = Long.parseLong(href.single("release"));
						
					final ActivityTemplate template = module.activityTemplates().get(templateId);
					final Activity activity = module.activities().get(activityId);
					final ActivityTemplateRelease release = template.releases().get(releaseId);
					
					release.applyTo(activity);
							
					return new RsForward(
							new RsFlash(
									String.format("La release %s a été appliquée avec succès !", release.version()),
				                Level.FINE
				            ),
				            "/home?activity=" + activity.id()
					);
				}
		);
	}
}
