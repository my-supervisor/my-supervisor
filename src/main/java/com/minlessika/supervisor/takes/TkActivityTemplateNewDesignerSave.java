package com.minlessika.supervisor.takes;

import java.util.Optional;
import java.util.logging.Level;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateNewDesignerSave extends TkBaseWrap {

	public TkActivityTemplateNewDesignerSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
						
					final Long activityId = Long.parseLong(new RqHref.Smart(req).single("activity"));
					final ActivityTemplate template = module.activityTemplates().get(activityId);
					
					final String email = form.single("email");		
					
					Optional<User> optUser = module.membership().users().userOf(email);
					if(!optUser.isPresent())
						throw new IllegalArgumentException("Cette adresse mail n'est pas lié à un utilisateur !");
					
					User user = optUser.get();
					template.changeDesigner(user);
					
					return new RsForward(
						new RsFlash(
							String.format("Le concepteur a été changé en %s avec succès ! L'activité ne figure plus dans vos modèles.", user.name()),
			                Level.FINE
			            ),
						"/activity/template"
				    );
				}
		);
	}	
}
