package com.supervisor.takes;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Sharing;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkSharingSave extends TkBaseWrap {

	public TkSharingSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					Sharing sharing = module.sharing();
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					Smart params = new RqHref.Smart(req);
					
					ResourceType type = ResourceType.valueOf(params.single("type"));
					Long resourceId = Long.parseLong(params.single("resource"));		
					Resource resource = module.resources().resource(type, resourceId);
					
					if(new RqUser(base, req).notOwn(resource)) {
						throw new IllegalArgumentException("Vous n'êtes pas propriétaire de cette ressource !");
					}
					
					// enregistrer les champs de données
					int nbOfFieldsToTreat = getValuesOfRow("item_email", form).size();
					for (int i = 0; i < nbOfFieldsToTreat; i++) {
						
						String email = getValuesOfRow("item_email", form).get(i);
						sharing.share(resourceId, type, email); 
					}
								
					final String msg = String.format("Le partage a été effectué avec succès !");
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
			            String.format("/sharing?type=%s&resource=%s", type.name(), resourceId)
				    );
				}
		);
	}

	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList((form.param(name).iterator()));
	}	
}
