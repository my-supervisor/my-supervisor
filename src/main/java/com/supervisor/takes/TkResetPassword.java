package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.codecs.MCodec;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetSafeMailing;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.codecs.Codec;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.misc.Utf8String;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;

public final class TkResetPassword extends TkBaseWrap {

	public TkResetPassword(Base base, final Recaptcha recaptcha) {
		super(
				base,
				req -> {
					Membership module = new DmMembership(base);
					
					final String key = new RqHref.Smart(req).single("key");
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String gRecaptchaResponse = form.single("g-recaptcha-response", "");
					recaptcha.validate(gRecaptchaResponse);
					
					final String password = form.single("password");
					final String confirmedPassword = form.single("password_confirmed");
					
					final Codec codec = new MCodec();
					
					Identity idt = codec.decode(
						                new Utf8String(key).asBytes()
						           );
					
					// vérifier la validité du lien
					LocalDate expirationDate = LocalDate.parse(
													idt.properties()
													.get("expiration_date")
											   );
					
					if(expirationDate.isBefore(LocalDate.now()))
						throw new IllegalArgumentException(I18n.localizeText("the_link_has_expired"));
							
					Optional<User> optUser = module.members()
							                       .userOf(idt.properties().get("email"));
					
					if(!optUser.isPresent())
						throw new IllegalArgumentException(I18n.localizeText("this_link_is_no_long_valid"));
					
					User user = optUser.get();	
					user.changePassword(user.password(), password, confirmedPassword); 
					
					new InternetSafeMailing().send(
							user.address().email(), 
							I18n.localizeText("reset_of_your_minlessika_password"), 
							String.format("<span>%s : </span><span>%s</span>", I18n.localizeText("your_new_password"), password)
					);
					
					return new RsForward(
			            		new RsFlash(
					                I18n.localizeText("your_password_has_been_successfully_reset"),
					                Level.FINE
					            ),
					            "/login"
						    );
				}
		);
	}
}
