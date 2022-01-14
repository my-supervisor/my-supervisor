package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.PreviousLocation;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.ActivityTemplatePublished;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplatePublishedLike extends TkBaseWrap {

	public TkActivityTemplatePublishedLike(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));					
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
