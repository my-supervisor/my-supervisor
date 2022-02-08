package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.PreviousLocation;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplatePublishedLike extends TkBaseWrap {

	public TkActivityTemplatePublishedLike(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));
					ActivityTemplatePublished template = module.activityTemplatesPublished().get(id);
					
					template.like();
					
					return new RsForward(
						new RsFlash(
			                "Merci pour votre appréciation !", 
			                Level.FINE
			            ),
						new PreviousLocation(req).toString()
				    );
				}
		);
	}	
}
