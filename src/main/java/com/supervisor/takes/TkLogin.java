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

import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.sdk.takes.XeRecaptcha;
import com.supervisor.sdk.translation.I18n;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.tk.TkWrap;

/**
 * Take that shows login page to user.
 *
 * @since 1.0
 */
public final class TkLogin extends TkWrap {

	public TkLogin(Recaptcha recaptcha) {
		super(
			req -> new RsAnonymousPage(
				I18n.localizeXslt("/xsl/login/page.xsl"),
				req,
				new XeChain(
					new XeAppend("menu", "login"),
					new XeAppend("lang", I18n.locale().getLanguage()),
					new XeRecaptcha(recaptcha)
				)
			)
		);
	}
}
