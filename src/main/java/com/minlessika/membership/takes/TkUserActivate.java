package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.Users;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;

import java.util.logging.Level;

public final class TkUserActivate extends TkBaseWrap {

	public TkUserActivate(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Users users = module.users();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
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
