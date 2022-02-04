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

import com.supervisor.domain.impl.UserAdmin;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.mailing.InternetWarningMailing;
import com.supervisor.sdk.secure.GRecaptcha;
import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.translation.I18n;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.tk.TkWrap;
import java.util.logging.Level;

/**
 * Take that allows an anonymous user to contact company team.
 *
 * @since 1.0
 */
public final class TkSendContact extends TkWrap {

	/**
	 * Ctor.
	 * @param base Data source
	 */
	public TkSendContact(final Base base) {
		super(
			req -> {
				final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
				final String gRecaptchaResponse = form.single("g-recaptcha-response", "");
				final Recaptcha recaptcha = new GRecaptcha(base.appInfo());
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
