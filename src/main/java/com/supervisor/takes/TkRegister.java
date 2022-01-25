package com.supervisor.takes;

import com.supervisor.domain.Language;
import com.supervisor.domain.Membership;
import com.supervisor.domain.RegistrationRequest;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetWarningMailing;
import com.supervisor.sdk.secure.GRecaptcha;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.BaseUri;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;

public final class TkRegister extends TkBaseWrap {

	public TkRegister(Base base) {
		super(
				base,
				req -> {
					Membership module = new DmMembership(base);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
							
					final String gRecaptchaResponse = form.single("g-recaptcha-response", ""); 
					
					Recaptcha recaptcha = new GRecaptcha(base.appInfo());
					recaptcha.validate(gRecaptchaResponse);
					
					final String name = form.single("name");
					final String email = form.single("email");
					final String password = form.single("password");
					final String confirmedPassword = form.single("confirmedPassword");
							
					final Language preferredLanguage = module.languages().where(Language::isoCode, I18n.locale().getLanguage()).first();
					
					RegistrationRequest request = module.registrationRequests()
							                            .request(name, email, password, confirmedPassword, preferredLanguage);
					
					String baseUri = new BaseUri(req).toString();
				    final String key = new String(
				    						Base64.getEncoder().encode(request.guid().toString().getBytes()), 
				    						StandardCharsets.UTF_8
				    				   );
				    final String link = String.format("%s/register/confirm?key=%s", baseUri, key);
				    
					new InternetWarningMailing().send(
							email, 
							I18n.localizeText("confirmation_request_of_your_minlessika_account"), 
							String.format("<span>%s :</span> <br/> <span>%s</span>", I18n.localizeText("to_confirm_your_subscription_please_copy_or_click_on_link_below"), link)
					);
					
					return new RsForward(
			            new RsFlash(
			            	I18n.localizeText("a_link_has_been_sent_by_mail_for_confirmation"),
			                Level.FINE
			            ),
			            "/login"
			        );
				}
		);
	}
}
