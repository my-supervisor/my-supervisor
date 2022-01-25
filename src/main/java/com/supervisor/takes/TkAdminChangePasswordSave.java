package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkAdminChangePasswordSave extends TkBaseWrap {

	public TkAdminChangePasswordSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					Long userId = Long.parseLong(new RqHref.Smart(req).single("user"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String password = form.single("password");
						
					final User item = module.users().get(userId);
					
					if(new RqUser(base, req).id() == item.id())
						throw new IllegalArgumentException("You can not change your password by this way !");
					
					item.changePassword(item.password(), password, password);
					
					return new RsForward(
			            	new RsFlash(
			            		"Your password has been successfully changed !",
				                Level.FINE
				            ),
				            String.format("/admin/user/edit?id=%s", item.id())
					    );
				}
		);
	}
}
