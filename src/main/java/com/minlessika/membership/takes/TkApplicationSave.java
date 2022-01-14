package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.domain.impl.PxAllApplications;
import com.minlessika.membership.domain.impl.PxProfiles;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkApplicationSave extends TkBaseWrap {

	public TkApplicationSave(Base base) {
		super(
				base,
				req -> {
					
					new RqAdminAuth(base, req);
					
					final Membership membership = new DmMembership(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));								
					final String module = form.single("module");										
					
					final Application item;
					final String msg;
					final String url;
					if(id > 0) {
						final Long profileId = Long.valueOf(form.single("profile_id"));
						final Profiles profiles = new PxProfiles(base, module);
						final Profile profile = profiles.get(profileId);
						item = new PxAllApplications(base).get(id);
						item.changeProfile(profile);
						
						msg = String.format("L'application %s a été modifiée avec succès !", item.module());
						url = "/admin/user/edit?id=" + item.user().id();
					}else {
						final Long userId = Long.valueOf(form.single("user_id"));
						final User user = membership.users().get(userId);
						item = user.applications().add(module);
						url = "/admin/user/app/edit?id=" + item.id();
						msg = String.format("L'application %s a été ajoutée avec succès !", item.module());
					}					
					
					return new RsForward(
								new RsFlash(
								    msg,
					                Level.FINE
					            ),
					            url
						    );
				}
		);
	}
}
