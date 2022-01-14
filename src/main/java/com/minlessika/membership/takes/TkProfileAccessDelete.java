package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkProfileAccessDelete extends TkBaseWrap {

	public TkProfileAccessDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
										
					final Long profileId = Long.parseLong(new RqHref.Smart(req).single("profile"));
					final Profile profile = new PxAllProfiles(base).get(profileId);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					ProfileAccess item = profile.accesses().get(id);
					String name = item.name();
					profile.accesses().remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("L'accès < %s > a été supprimé avec succès !", name),
			                Level.FINE
			            ),
			            String.format("/admin/profile/edit?id=%s", profile.id())
				    );
				}
		);
	}
}
