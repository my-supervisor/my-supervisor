package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.Users;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.UUID;
import java.util.logging.Level;

public final class TkUserActivate extends TkBaseWrap {

	public TkUserActivate(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Users users = module.users();
					final UUID id = UUID.fromString(new RqHref.Smart(req).single("id"));
					final Boolean active = Boolean.parseBoolean(new RqHref.Smart(req).single("active"));
					
					User item = users.get(id);
					item.activate(active);
					final String msg;
					
					if(item.active())
						msg = String.format("L'utilisateur < %s > a été activé avec succès !", item.name());
					else
						msg = String.format("L'utilisateur < %s > a été désactivé avec succès !", item.name());
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
						String.format("/admin/user/edit?id=%s", item.id())
				    );
				}
		);
	}
}
