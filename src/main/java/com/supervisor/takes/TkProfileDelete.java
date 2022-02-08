package com.supervisor.takes;

import com.supervisor.domain.AllProfiles;
import com.supervisor.domain.Profile;
import com.supervisor.domain.impl.PxAllProfiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.UUID;
import java.util.logging.Level;

public final class TkProfileDelete extends TkBaseWrap {

	public TkProfileDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
												
					final AllProfiles profiles = new PxAllProfiles(base);
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));

					Profile item = profiles.get(id);
					String name = item.name();
					profiles.remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("Le profil < %s > a été supprimé avec succès !", name),
			                Level.FINE
			            ),
						"/admin/profile"
				    );
				}
		);
	}
}
