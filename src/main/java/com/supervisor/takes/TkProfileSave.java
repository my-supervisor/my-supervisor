package com.supervisor.takes;

import com.supervisor.domain.Profile;
import com.supervisor.domain.Profiles;
import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkProfileSave extends TkBaseWrap {

	public TkProfileSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id", "0"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));			
					final String name = form.single("name");
					
					final Profiles profiles = new PxProfiles(base);
					
					final Profile item;
					
					if(id > 0) {
						item = profiles.get(id);	
						item.update(item.code(), name);
						
						Profile parent = Profile.EMPTY;
						final Long parentId = Long.parseLong(form.single("parent_id"));
						if(parentId > 0)
							parent = profiles.get(parentId);
						
						item.changeParent(parent);
						
						String msg = String.format("Le profil < %s > a été modifié avec succès !", item.name());
						
						return new RsForward(
								new RsFlash(
								    msg,
					                Level.FINE
					            ),
					            "/admin/profile"
						    );
					}else {
						final String code = form.single("code");
						item = profiles.add(code, name);
						
						String msg = String.format("Le profil < %s > a été créé avec succès !", item.name());
						
						return new RsForward(
								new RsFlash(
								    msg,
					                Level.FINE
					            ),
					            String.format("/admin/profile/edit?id=%s", item.id())
						    );
					}
				}
		);
	}
}
