package com.supervisor.takes;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.User;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkActivityTemplateNewDesignerSave extends TkBaseWrap {

	public TkActivityTemplateNewDesignerSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
						
					final UUID activityId = UUID.fromString(new RqHref.Smart(req).single("activity"));
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
