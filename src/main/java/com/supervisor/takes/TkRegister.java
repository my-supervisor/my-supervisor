/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import com.supervisor.sdk.translation.I18n;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.tk.TkWrap;

/**
 * Take that registers a new user.
 *
 * @since 1.0
 */
public final class TkRegister extends TkWrap {

	/**
	 * Ctor.
	 * @param base Data source
	 */
	public TkRegister(Base base) {
		super(
			req -> {
				final Membership module = new DmMembership(base);
				final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
				final String gRecaptchaResponse = form.single("g-recaptcha-response", "");
				// Recaptcha recaptcha = new GRecaptcha(base.appInfo());
				// recaptcha.validate(gRecaptchaResponse);
				final String name = form.single("name");
				final String email = form.single("email");
				final String password = form.single("password");
				final String confirmedPassword = form.single("confirmedPassword");
				final Language preferredLanguage = module.languages().where(Language::isoCode, I18n.locale().getLanguage()).first();
				final RegistrationRequest request = module.registrationRequests()
					.request(name, email, password, confirmedPassword, preferredLanguage);
				final String baseUri = new BaseUri(req).toString();
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
