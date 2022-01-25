package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.codecs.MCodec;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetWarningMailing;
import com.supervisor.sdk.secure.GRecaptcha;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.BaseUri;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.codecs.Codec;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.misc.Utf8String;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public final class TkRequestPasswordResetting extends TkBaseWrap {

	public TkRequestPasswordResetting(Base base) {
		super(
				base,
				req -> {
					Membership module = new DmMembership(base);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));

					final String gRecaptchaResponse = form.single("g-recaptcha-response", ""); 
					System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
					
					Recaptcha recaptcha = new GRecaptcha(base.appInfo());
					recaptcha.validate(gRecaptchaResponse);
					
					final String email = form.single("email");
					
					Optional<User> optUser = module.members().userOf(email);
					if(!optUser.isPresent())
						throw new IllegalArgumentException(I18n.localizeText("this_user_does_not_exist"));
					
					User user = optUser.get();    
				    Map<String, String> props = new HashMap<>();
					props.put("id", user.id().toString());
					props.put("name", user.name());
					props.put("email", user.address().email());
					props.put("password", user.password());
					props.put(
							"expiration_date", 
							LocalDate.now().plusDays(1L).toString()
					);
					
					Identity idt = new Identity.Simple(
										String.format("urn:minlessika:%d", user.id()),
										props
								   );
					
					final Codec codec = new MCodec();
					
					String baseUri = new BaseUri(req).toString();
				    final String key = new Utf8String(codec.encode(idt)).asString();
				    final String link = String.format("%s/password/reset/change?key=%s", baseUri, key);
				    
				    new InternetWarningMailing().send(
							email, 
							I18n.localizeText("reset_request_of_your_minlessika_password"), 
							String.format("<span>%s : </span> <br/> <span>%s</span>", I18n.localizeText("you_have_requested_an_reset_of_your_password"), link)
					);
				    
					return new RsForward(
			            	new RsFlash(
			            			I18n.localizeText("an_email_has_been_sent_to_you_to_finalize_the_reset_of_your_password"),
					                Level.FINE
				            ),
				            "/"
				    );
				}
		);
	}
}
