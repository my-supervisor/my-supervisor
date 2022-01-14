package com.minlessika.membership.takes;

import com.minlessika.membership.domain.AllProfiles;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkProfileDelete extends TkBaseWrap {

	public TkProfileDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
												
					final AllProfiles profiles = new PxAllProfiles(base);
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

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
