package com.supervisor.takes;

import com.supervisor.domain.impl.UserAdmin;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetWarningMailing;
import com.supervisor.sdk.secure.GRecaptcha;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.logging.Level;

public final class TkSendContact extends TkBaseWrap {

	public TkSendContact(final Base base) {
		super(
				base,
				req -> {
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String gRecaptchaResponse = form.single("g-recaptcha-response", ""); 
					
					Recaptcha recaptcha = new GRecaptcha(base.appInfo());
					recaptcha.validate(gRecaptchaResponse);
					
					final String name = form.single("name");
					final String email = form.single("email");
					final String message = form.single("message");

					new InternetWarningMailing().send(
							new UserAdmin(base).address().email(), 
							String.format("Contact : %s", name), 
							String.format("<span>%s (%s) sent you this message :</span> <br /> <span>%s</span>", name, email, message)
					);
					
					final String msg = I18n.localizeText("your_mail_has_been_sent_to_us_with_success");
					
					return new RsForward(
						new RsFlash(
			                msg,
			                Level.FINE
			            ),
						"/"
				    );
				}
		);
	}
}
