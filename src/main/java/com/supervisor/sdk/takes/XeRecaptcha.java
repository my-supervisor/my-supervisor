package com.supervisor.sdk.takes;

import com.supervisor.sdk.secure.Recaptcha;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

public final class XeRecaptcha extends XeWrap {

	public XeRecaptcha(final Recaptcha recaptcha) {
	        super(
				() -> new Directives()
					.add("recaptcha")
					.add("active").set(recaptcha.isActive()).up()
					.add("site-key").set(recaptcha.siteKey()).up()
					.up()
			);
	    }
}
