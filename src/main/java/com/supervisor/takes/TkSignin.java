package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.Users;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.MIdentity;
import com.supervisor.sdk.codecs.MCodec;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.secure.GRecaptcha;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkSignin extends TkBaseWrap {

	public TkSignin(Base base) {
		super(
				base,
				req -> {
					
					final Membership module = new DmMembership(base);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));

					final String gRecaptchaResponse = form.single("g-recaptcha-response", "");
					
					final Recaptcha recaptcha = new GRecaptcha(base.appInfo());
					recaptcha.validate(gRecaptchaResponse);
					
					final String email = form.single("email");
					final String password = form.single("password");
					
					final Users users = module.members();
					final User userFound = users.signin(email, password, false);			
					I18n.register(userFound.locale());
					
					final Identity idt = new MIdentity(userFound);
					
					return new PsCookie(
			                	new MCodec(TkApp.PASS_PHRASE)
			                )
							.exit(
								new RsForward(
					            	new RsFlash(
						                String.format("%s %s !", I18n.localizeText("welcome"), userFound.name()),
						                Level.FINE
						            ),
						            "/home"
							    ), 
								idt
							 );
				}
		);
	}
}
