package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.ResourceType;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkSharingDelete extends TkBaseWrap {

	public TkSharingDelete(Base base) {
		super(
				base, 
				(req)->{
					final Supervisor module = new PxSupervisor(base, req);
					Smart params = new RqHref.Smart(req);
					
					Long id = Long.parseLong(params.single("id"));
									
					SharedResource resource = module.sharing().get(id);
					String name = resource.subscriber().name();
					ResourceType type = resource.type();
					Long resourceId = resource.resourceId();					
					
					module.sharing().remove(resource);
								
					return new RsForward(
						new RsFlash(
			                String.format("L'utilisateur %s ne suit plus cette resource !", name),
			                Level.FINE
			            ),
						String.format("/sharing?type=%s&resource=%s", type.name(), resourceId)
				    );
				}
		);
	}
}
