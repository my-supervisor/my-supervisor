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

public final class TkUserDelete extends TkBaseWrap {

	public TkUserDelete(Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);			
					final Users users = module.users();
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));

					User item = users.get(id);
					String name = item.name();
					users.remove(item);
					
					return new RsForward(
						new RsFlash(
			                String.format("L'utilisateur < %s > a été supprimé avec succès !", name),
			                Level.FINE
			            ),
						"/admin/user"
				    );
				}
		);
	}
}
